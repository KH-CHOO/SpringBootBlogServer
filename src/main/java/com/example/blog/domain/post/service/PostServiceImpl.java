package com.example.blog.domain.post.service;

import com.example.blog.domain.comment.repository.CommentLikeRepository;
import com.example.blog.domain.post.dto.PostRequestDTO;
import com.example.blog.domain.post.dto.PostResponseDTO;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.exception.PostNotFoundException;
import com.example.blog.domain.post.repository.PostLikeRepository;
import com.example.blog.domain.post.repository.PostRepository;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.entity.UserRoleEnum;
import com.example.blog.domain.user.exception.UserNotFoundException;
import com.example.blog.domain.user.repository.UserRepository;
import com.example.blog.domain.user.service.S3Service;
import com.example.blog.global.dto.StatusAndMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    private final S3Service s3Service;

    private final MessageSource messageSource;

    // 게시글 생성
    @Transactional
    @Override
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Not Found User")
        );

        String imgFileUrl = null;

        MultipartFile imageFile = postRequestDTO.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imgFileUrl = s3Service.uploadFile(imageFile);
            } catch (Exception e) {
                System.err.println("Failed to upload file to S3: " + e.getMessage());
                throw e;
            }
        }

        Post post = Post.builder()
                .postImageUrl(imgFileUrl)
                .title(postRequestDTO.getTitle())
                .content(postRequestDTO.getContent())
                .user(user)
                .build();
        Post savedPost = postRepository.save(post);

        PostResponseDTO response = new PostResponseDTO(savedPost);
        return response;
    }


    // 게시글 전체 조회
    @Transactional
    @Override
    public Page<PostResponseDTO> getPosts(int page) {

        // 페이징 기능
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, 15, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        // 페이지 수 이상으로 page 가 들아올 때
        if (page >= posts.getTotalPages()) {
            throw new IllegalArgumentException("게시글이 없습니다.");
        }

        posts.forEach(post -> {
            // 게시글 좋아요 수
            post.setLikeCount(postLikeRepository.countByPostId(post.getId()));

            // 댓글 좋아요 수
            post.getComments().forEach(comment ->
                    comment.setLikeCount(commentLikeRepository.countByCommentId(comment.getId())));
        });

        return posts.map(PostResponseDTO::new);
    }

    @Transactional
    @Override
    public Page<PostResponseDTO> getTrendingPosts(int page) {
        // 페이징 기능
        Sort sort = Sort.by(Sort.Direction.DESC, "likeCount", "createdDate");
        Pageable pageable = PageRequest.of(page, 15, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        // 페이지 수 이상으로 page 가 들아올 때
        if (page >= posts.getTotalPages()) {
            throw new IllegalArgumentException("게시글이 없습니다.");
        }

        posts.forEach(post -> {
            // 게시글 좋아요 수
            post.setLikeCount(postLikeRepository.countByPostId(post.getId()));

            // 댓글 좋아요 수
            post.getComments().forEach(comment ->
                    comment.setLikeCount(commentLikeRepository.countByCommentId(comment.getId())));
        });

        return posts.map(PostResponseDTO::new);
    }

    // 게시글 지정 조회
    @Transactional(readOnly = true)
    @Override
    public PostResponseDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Not Found Post"));

        // 게시글 좋아요 수
        post.setLikeCount(postLikeRepository.countByPostId(post.getId()));

        // 댓글 좋아요 수
        post.getComments().forEach(comment ->
                comment.setLikeCount(commentLikeRepository.countByCommentId(comment.getId())));

        PostResponseDTO response = new PostResponseDTO(post);
        return response;
    }


    // 게시글 수정
    @Transactional
    @Override
    public PostResponseDTO modifyPost(Long postId, PostRequestDTO postRequestDTO, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Not Found User")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Not Found Post")
        );

        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!(post.getUser().getId().equals(user.getId()))) {
                throw new IllegalArgumentException();
            }

        }

        // 기존 이미지 url 불러오기
        String imgFileUrl = post.getPostImageUrl();

        MultipartFile imageFile = postRequestDTO.getImageFile();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                imgFileUrl = s3Service.uploadFile(imageFile);
            } catch (Exception e) {
                System.err.println("Failed to upload file to S3: " + e.getMessage());
                throw e;
            }
        }

        if (validationAuthority(user, post)) {
            post.modifyPost(imgFileUrl, postRequestDTO.getTitle(), postRequestDTO.getContent());
        } else {
            throw new IllegalArgumentException();
        }

        // 게시글 좋아요 수
        post.setLikeCount(postLikeRepository.countByPostId(post.getId()));

        // 댓글 좋아요 수
        post.getComments().forEach(comment ->
                comment.setLikeCount(commentLikeRepository.countByCommentId(comment.getId())));

        PostResponseDTO response = new PostResponseDTO(post);
        return response;
    }


    // 게시글 삭제
    @Transactional
    @Override
    public StatusAndMessageDTO deletePost(Long postId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("Not Found User")
        );

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Not Found Post")
        );

        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!(post.getUser().getId().equals(user.getId()))) {
                throw new IllegalArgumentException();
            }
        }
        if (validationAuthority(user, post)) {
            postRepository.delete(post);
            return new StatusAndMessageDTO(200, "게시글 삭제 완료");
        } else {
            throw new IllegalArgumentException();
        }
    }

    // 수정, 삭제시 권한 확인
    private boolean validationAuthority(User user, Post post) {
        if (!user.getRole().equals(UserRoleEnum.ADMIN)) {
            if (!(post.getUser().getId().equals(user.getId()))) {
                return false;
            }
        }
        return true;
    }
}
