package com.sparta.rooibos.user.application.dto.request;

import com.sparta.rooibos.user.domain.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// @ToString(exclude = "password")
public record UserRequest (
        @NotBlank(message = "사용자 이름은 필수입니다.")
        @Size(min = 3, max = 20, message = "사용자 이름은 3자 이상 20자 이하로 입력해야 합니다.")
        String username,

        @NotBlank(message = "사용자 아이디는 필수입니다.")
        @Size(min = 4, max = 10, message = "아이디는 4자 이상 10자 이하로 입력해야 합니다.")
        @Pattern(
                regexp = "^[a-z0-9]{4,10}$",
                message = "아이디는 소문자(a~z)와 숫자(0~9)로만 구성되어야 합니다."
        )
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 8, max = 15, message = "비밀번호는 8자 이상 15자 이하로 입력해야 합니다.")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,15}$",
                message = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함해야 하며 8~15자여야 합니다."
        )
        String password,

        @NotBlank(message = "슬랙 계정은 필수입니다.")
        String slackAccount,

        @NotBlank(message = "전화번호는 필수입니다.")
        String phone,

        Role role
){}