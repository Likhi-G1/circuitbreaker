package com.example.loadbalancer.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyService {

    private static final String REMOTE_SERVICE_URL = "http://localhost:8081/remoteService";
    private final RestTemplate restTemplate;

    public MyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "remoteService", fallbackMethod = "fallbackMethod")
    @Retry(name = "remoteService")
    @RateLimiter(name = "remoteService")
    public String callRemoteService() {
        return restTemplate.getForObject(REMOTE_SERVICE_URL, String.class);
    }

    public String fallbackMethod(Exception e) {
        return "Fallback response: Remote service is currently unavailable.";
    }
}

