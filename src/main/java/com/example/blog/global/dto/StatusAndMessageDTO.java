package com.example.blog.global.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "Status and Message")
@Getter
@AllArgsConstructor
public class StatusAndMessageDTO {

    private int status;
    private String message;

}
