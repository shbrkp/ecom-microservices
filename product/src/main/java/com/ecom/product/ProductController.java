package com.ecom.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping("/api/product")
    public String getUser(){
        return "Product module";
    }
}
