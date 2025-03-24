package com.sparta.rooibos.deliverer.application.dto.request;

public record DelivererSearchRequest(
     String keyword,
     String sort,
     String filter,
     int page,
     int size
){ }