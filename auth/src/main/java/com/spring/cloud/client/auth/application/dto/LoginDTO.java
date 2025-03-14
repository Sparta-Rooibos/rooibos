package com.spring.cloud.client.auth.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
//record
@Getter
public class LoginDTO {

    @NotBlank(message = "아이디를 입력하세요.")
    private String username;
    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}

