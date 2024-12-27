package com.example.loadbalancer.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@Service
public class MyService {

    private static final String REMOTE_SERVICE_URL = "http://localhost:8081/remoteService";
    private static final Logger logger = LoggerFactory.getLogger(MyService.class);

    private final RestTemplate restTemplate;

    public MyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "remoteService", fallbackMethod = "fallbackMethod")
    @Retry(name = "remoteService")
    @RateLimiter(name = "remoteService")
    public String callRemoteService() {
        logger.info("Calling remote service...");
        return restTemplate.getForObject(REMOTE_SERVICE_URL, String.class);
    }

    // Fallback method for circuit breaker
    public String fallbackMethod(Throwable e) {
        logger.error("Fallback triggered due to: {}", e.getMessage());
        return "Fallback: Remote service is unreachable.";
    }

}
