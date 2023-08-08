package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenVerificationServiceImpl implements TokenVerificationService{
    private static final String TOKEN_CACHE = "tokenCache";
    private static final Duration TOKEN_EXPIRATION = Duration.ofHours(2);

    private final RedisTemplate<String, Boolean> redisTemplate;

    @Override
    public String generateToken() {
        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, true, TOKEN_EXPIRATION);
        return token;
    }

    @Override
    @Cacheable(value = TOKEN_CACHE, key = "#token")
    public Boolean verifyToken(String token) {
        Boolean isValid = redisTemplate.opsForValue().get(token);
        return isValid != null ? isValid : false;
    }
}
