package com.ecom.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/api/user")
    public String getUser(){
        return "User module";
    }
}
