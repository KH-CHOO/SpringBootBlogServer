package com.example.blog.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "좋아요 응답 DTO")
@Getter
@AllArgsConstructor
public class LikeResponseDTO {
    private int status;
    private String message;
}
