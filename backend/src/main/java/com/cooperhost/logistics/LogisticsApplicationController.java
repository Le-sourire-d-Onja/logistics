package com.cooperhost.logistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogisticsApplicationController {
    @Autowired
    private LogisticsApplicationService logisticsApplicationService;

    @GetMapping("/")
    public String hello() {
        return logisticsApplicationService.hello();
    } 
}
