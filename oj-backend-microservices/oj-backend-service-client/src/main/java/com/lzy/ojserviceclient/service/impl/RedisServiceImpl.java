package com.lzy.ojserviceclient.service.impl;

import com.lzy.ojserviceclient.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final long EXPIRATION = TimeUnit.HOURS.toMillis(2);

    @Override
    public void storeToken(String token, Long userId) {
        stringRedisTemplate.opsForValue().set("token:" + token, userId.toString(), EXPIRATION, TimeUnit.MILLISECONDS);
    }

    @Override
    public boolean isTokenValid(String token) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey("token:" + token));
    }

    @Override
    public void removeToken(String token) {
        stringRedisTemplate.delete("token:" + token);
    }
}
