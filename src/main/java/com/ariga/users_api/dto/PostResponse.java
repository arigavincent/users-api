package com.ariga.users_api.dto;
import java.time.Instant;
public record PostResponse(
        Long id,
        String title,
        String content,
        Long userId,
        String authorName,
        Instant createdAt) {}