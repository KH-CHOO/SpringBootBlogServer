package com.example.blog.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "게시글 request DTO")
@Getter
public class PostRequestDTO {

    @NotBlank(message = "제목은 공백이 불가능 합니다.")
    private final String title;

    private final String content;

    private MultipartFile imageFile;

    public PostRequestDTO(String title, String content, MultipartFile imageFile) {
        this.title = title;
        this.content = content;
        this.imageFile = imageFile;
    }
}
