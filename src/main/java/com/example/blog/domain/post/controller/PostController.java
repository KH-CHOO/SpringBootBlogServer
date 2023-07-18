package com.example.blog.domain.post.controller;

import java.util.List;
import java.util.Map;

import com.example.blog.domain.comment.service.CommentService;
import com.example.blog.domain.post.service.PostService;
import com.example.blog.domain.user.service.S3Service;
import com.example.blog.global.dto.StatusAndMessageDTO;
import com.example.blog.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.blog.domain.post.dto.PostRequestDTO;
import com.example.blog.domain.post.dto.PostResponseDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "게시글 기능")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://locallhost:3000", allowedHeaders = { "Authorization", "Cache-Control", "Content-Type" }, exposedHeaders = "Authorization")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @Operation(summary = "게시글 등록")
    @PostMapping(value = "/posts", consumes = {"multipart/form-data"})
    public ResponseEntity<PostResponseDTO> createPost(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("imageFile") MultipartFile imageFile,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        PostRequestDTO postRequestDTO = new PostRequestDTO(title, content, imageFile);
        PostResponseDTO response = postService.createPost(postRequestDTO, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "게시글 전체 조회")
    // 게시글 전체 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDTO>> getPosts() {
        List<PostResponseDTO> response = postService.getPosts();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "게시글 조회")
    // 게시글 지정 조회
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> getPost(@PathVariable("postId") Long postId) {
        PostResponseDTO response = postService.getPost(postId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "게시글 수정")
    // 게시글 수정
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponseDTO> modifyPost(@PathVariable("postId") Long postId,
                                                      @RequestParam("title") String title,
                                                      @RequestParam("content") String content,
                                                      @RequestParam("imageFile") MultipartFile imageFile,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostRequestDTO postRequestDTO = new PostRequestDTO(title, content, imageFile);
        PostResponseDTO response = postService.modifyPost(postId, postRequestDTO, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "게시글 삭제")
    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<StatusAndMessageDTO> deletePost(@PathVariable("postId") Long postId
                                                        , @AuthenticationPrincipal UserDetailsImpl userDetails){
        StatusAndMessageDTO response = postService.deletePost(postId, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
