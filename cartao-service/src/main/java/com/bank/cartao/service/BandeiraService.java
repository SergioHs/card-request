package com.bank.cartao.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.NavigableMap;
import java.util.TreeMap;

@Service
public class BandeiraService {

    private final NavigableMap<Integer, String> ranges = new TreeMap<>();
    private final RedisTemplate<String, String> redisTemplate;


    public BandeiraService(@Qualifier("redisTemplate") RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void init() {
        redisTemplate.opsForValue().set("bandeira:401-600", "master");
        redisTemplate.opsForValue().set("bandeira:600-700", "visa");
        redisTemplate.opsForValue().set("bandeira:700-800", "diners club");
        redisTemplate.opsForValue().set("bandeira:800-1000", "elo");
    }

    public String determinarBandeira(int score) {
        if (score >= 401 && score < 600) return redisTemplate.opsForValue().get("bandeira:401-600");
        if (score >= 600 && score < 700) return redisTemplate.opsForValue().get("bandeira:600-700");
        if (score >= 700 && score < 800) return redisTemplate.opsForValue().get("bandeira:700-800");
        if (score >= 800 && score <= 1000) return redisTemplate.opsForValue().get("bandeira:800-1000");
        return null;
    }
}
