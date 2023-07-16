package com.example.blog.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Schema(description = "회원가입 응답 DTO")
@Getter
@AllArgsConstructor
public class SignupResponseDTO {
    private int status;
    private String message;
}
