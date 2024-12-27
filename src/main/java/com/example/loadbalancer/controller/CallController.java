package com.example.loadbalancer.controller;

import com.example.loadbalancer.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallController {

    @Autowired
    private MyService myService;

    @GetMapping("/call")
    public String callService() {
        return myService.callRemoteService();
    }
}
