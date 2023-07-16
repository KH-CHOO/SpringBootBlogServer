package com.example.blog.domain.comment.dto;

import com.example.blog.domain.comment.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Schema(description = "댓글 응답 DTO")
@Getter
public class CommentResponseDTO {
    private final Long commentId;
    private final String content;
    private final LocalDateTime createDate;
    private final String username;

    private int likeCount;
    public CommentResponseDTO(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.createDate = comment.getCreatedDate();
        this.username = comment.getUser().getUsername();
        this.likeCount = comment.getLikeCount();
    }
}
