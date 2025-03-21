package com.sparta.rooibos.user.application.dto;

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
        @Size(min = 3, max = 20, message = "사용자 이름은 3자 이상 20자 이하로 입력해야 합니다.")
        String email,

        @NotBlank(message = "비밀번호는 필수입니다.")
        @Size(min = 4, message = "비밀번호는 최소 4자 이상이어야 합니다.")
        @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*?&]{4,}$",
            message = "비밀번호는 최소 4자 이상, 하나 이상의 영문자 및 숫자를 포함해야 합니다."
        )
        String password,

        @NotBlank(message = "슬랙 계정은 필수입니다.")
        String slackAccount,

        @NotBlank(message = "전화번호는 필수입니다.")
        String phone,

        @NotBlank(message = "권한은 필수입니다.")
        Role role
){}