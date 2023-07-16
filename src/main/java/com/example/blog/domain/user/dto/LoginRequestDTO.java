package com.example.blog.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
@Schema(description = "로그인 요청 DTO")
@Getter
public class LoginRequestDTO {

    private String username;
    private String password;
}