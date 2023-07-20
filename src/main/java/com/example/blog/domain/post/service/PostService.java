package com.example.blog.domain.post.service;

import com.example.blog.domain.post.dto.PostRequestDTO;
import com.example.blog.domain.post.dto.PostResponseDTO;
import com.example.blog.global.dto.StatusAndMessageDTO;
import org.springframework.data.domain.Page;

public interface PostService {

    PostResponseDTO createPost(PostRequestDTO postRequestDTO, String username);

    Page<PostResponseDTO> getPosts(int page);

    PostResponseDTO getPost(Long postId);
    Page<PostResponseDTO> getTrendingPosts(int page);

    PostResponseDTO modifyPost(Long postId, PostRequestDTO postRequestDTO, String username);

    StatusAndMessageDTO deletePost(Long postId, String username);


}
