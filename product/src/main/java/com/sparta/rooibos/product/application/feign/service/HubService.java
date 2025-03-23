package com.sparta.rooibos.product.application.feign.service;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public interface HubService {

    UUID getHubManager(@RequestParam String email);
}
