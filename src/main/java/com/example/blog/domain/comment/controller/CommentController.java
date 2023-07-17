package com.example.blog.domain.comment.controller;

import com.example.blog.domain.comment.dto.CommentRequestDTO;
import com.example.blog.domain.comment.dto.CommentResponseDTO;
import com.example.blog.domain.comment.service.CommentService;
import com.example.blog.global.dto.StatusAndMessageDTO;
import com.example.blog.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "댓글 기능")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 작성")
    // 댓글 생성
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        CommentResponseDTO response = commentService.createComment(commentRequestDTO, userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "댓글 수정")
    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> modifyComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO,
                                                            @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                            @PathVariable("commentId") Long commentId) {
        CommentResponseDTO response = commentService.modifyComment(commentId, commentRequestDTO,  userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "댓글 삭제")
    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<StatusAndMessageDTO> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @PathVariable("commentId") Long commentId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.deleteComment(commentId, userDetails.getUsername()));
    }


}
