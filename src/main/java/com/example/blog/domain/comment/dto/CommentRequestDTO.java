package com.example.blog.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
@Schema(description = "댓글 request DTO")
@Getter
public class CommentRequestDTO {

    private final Long postId;

    @NotBlank(message = "댓글은 공백이 불가능 합니다.")
    private final String content;

    public CommentRequestDTO(Long postId, String content) {
        this.postId = postId;
        this.content = content;
    }
}
