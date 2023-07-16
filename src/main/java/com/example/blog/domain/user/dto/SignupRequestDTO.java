package com.example.blog.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Schema(description = "회원가입 요청 DTO")
@Getter
public class SignupRequestDTO {

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])"
            + "[\\da-z]{4,10}$"
            , message = "유저명은 숫자와 영소문자로만 구성하되 각 1개 이상을 포함해야 합니다. 또한 길이가 4이상 10이하가 되어야 합니다.")
    private String username;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[A-Za-z])(?=.*[!@#$%^&*()-+])[A-Za-z\\d!@#$%^&*()-+]{8,15}$"
            , message = "비밀번호는 숫자, 알파벳, 특수문자 !@#$%^&*()-+ 로만 구성하되 각 1개 이상 포함해야 합니다. 또한 길이가 8이상 15이하가 되어야 합니다.")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}
