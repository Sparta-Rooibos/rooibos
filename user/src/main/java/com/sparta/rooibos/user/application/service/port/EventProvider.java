package com.sparta.rooibos.user.application.service.port;

public interface EventProvider {
  void blacklistUser(String email, long ttlSeconds);
}