package com.example.blog.domain.comment.service;

import com.example.blog.domain.comment.dto.CommentRequestDTO;
import com.example.blog.domain.comment.dto.CommentResponseDTO;
import com.example.blog.global.dto.StatusAndMessageDTO;
import jdk.jshell.Snippet;

import java.util.List;
import java.util.Map;

public interface CommentService {

    List<CommentResponseDTO> getCommentsByPostId(Long postId);
    CommentResponseDTO createComment(CommentRequestDTO crqd, String username);

    CommentResponseDTO modifyComment(Long commentId, CommentRequestDTO crqd, String username);

    StatusAndMessageDTO deleteComment(Long commentId, String username);
}
