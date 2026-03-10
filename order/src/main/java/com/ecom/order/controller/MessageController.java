package com.ecom.order.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class MessageController {

    @Value("${app.message:Default Hello1}")
    private String message;

    @GetMapping("/message")
    @RateLimiter(name="rateBreaker", fallbackMethod = "getMessageFallback")
    public String getMessage(){
        return message;
    }

    public String getMessageFallback(Exception ex){
        return "Hello Fallback";
    }
}
