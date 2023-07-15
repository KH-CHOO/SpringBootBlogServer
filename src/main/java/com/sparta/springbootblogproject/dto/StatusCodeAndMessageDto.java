package com.sparta.springbootblogproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class StatusCodeAndMessageDto {
    private int statusCode;
    private String message;
}
