package com.sparta.rooibos.user.application.dto.request;


public record UserUpdateRequest (
    String password,
    String slackAccount,
    String phone
){}
