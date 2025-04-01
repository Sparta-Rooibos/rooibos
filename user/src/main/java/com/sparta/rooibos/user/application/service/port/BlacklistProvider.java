package com.sparta.rooibos.user.application.service.port;

public interface BlacklistProvider {
    void addToBlacklist(String email, long ttlSeconds);
}