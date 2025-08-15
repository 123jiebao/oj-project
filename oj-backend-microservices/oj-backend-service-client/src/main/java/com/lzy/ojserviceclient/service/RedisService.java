package com.lzy.ojserviceclient.service;

public interface RedisService {


    void storeToken(String token, Long userId);

    boolean isTokenValid(String token);

    void removeToken(String token);
}
