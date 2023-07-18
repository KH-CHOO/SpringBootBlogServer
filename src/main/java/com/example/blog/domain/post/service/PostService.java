package com.example.blog.domain.post.service;

import com.example.blog.domain.post.dto.PostRequestDTO;
import com.example.blog.domain.post.dto.PostResponseDTO;
import com.example.blog.global.dto.StatusAndMessageDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    PostResponseDTO createPost(PostRequestDTO postRequestDTO, String username);

    Page<PostResponseDTO> getPosts(int page);

    PostResponseDTO getPost(Long postId);

    PostResponseDTO modifyPost(Long postId, PostRequestDTO postRequestDTO, String username);

    StatusAndMessageDTO deletePost(Long postId, String username);
}
