package com.sparta.rooibos.user.application.dto.request;

public record UserSearchRequest (
     String keyword,
     String sort,
     String filter,
     int page,
     int size
){ }