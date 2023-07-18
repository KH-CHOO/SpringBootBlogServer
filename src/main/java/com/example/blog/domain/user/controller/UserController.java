package com.example.blog.domain.user.controller;

import com.example.blog.domain.user.dto.LoginRequestDTO;
import com.example.blog.domain.user.dto.SignupRequestDTO;
import com.example.blog.domain.user.dto.SignupResponseDTO;
import com.example.blog.domain.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 기능")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://locallhost:3000", allowedHeaders = { "Authorization", "Cache-Control", "Content-Type" }, exposedHeaders = "Authorization")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입")
    @PostMapping("/user/signup")
    public ResponseEntity<SignupResponseDTO> signup(@RequestBody @Valid SignupRequestDTO signupRequestDto) {
        return userService.signup(signupRequestDto);
    }

    @Operation(summary = "로그인")
    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        // 컨트롤러 도달하기 전에 JwtAuthenticationFilter가 가로챔 <- 로그인시 JWT 인증은 시큐리티가 하도록 함
        return ResponseEntity.ok().build();
    }
}
