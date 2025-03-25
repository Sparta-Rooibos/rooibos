package com.sparta.rooibos.client.application.dto.response;


import com.sparta.rooibos.client.application.feigin.service.dto.ManageHub;

import java.time.LocalDateTime;

public record GetClientResponse(String id, String name, String address, String type, ManageHub manageHub, LocalDateTime createdAt, LocalDateTime updatedAt) {}
