package com.example.blog.domain.user.controller;

import com.example.blog.domain.user.dto.LikeResponseDTO;
import com.example.blog.domain.user.service.LikeService;
import com.example.blog.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "좋아요 기능")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = { "Authorization", "Cache-Control", "Content-Type" }, exposedHeaders = "Authorization")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "게시글 좋아요")
    @PostMapping("/posts/{postId}/like")
    public ResponseEntity<LikeResponseDTO> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.togglePostLike(postId, userDetails.getUser().getId());
    }

    @Operation(summary = "댓글 좋아요")
    @PostMapping("/comments/{commentId}/like")
    public ResponseEntity<LikeResponseDTO> likeComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.toggleCommentLike(commentId, userDetails.getUser().getId());
    }
}