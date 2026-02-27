package com.ecom.order.controller;

import com.ecom.order.dto.CartItemRequest;
import com.ecom.order.model.CartItem;
import com.ecom.order.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addCart(
            @RequestHeader("X-User-ID") String userId,
            @RequestBody CartItemRequest cartItemRequest){
        if(!cartService.addToCart(userId,cartItemRequest))
            return ResponseEntity.badRequest().body("Invalid product or less quantity or invalid user");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> deleteCartItem(
            @RequestHeader("X-User-ID")  String userId,
            @PathVariable String productId){
        if(!cartService.deleteCartItem(userId,productId))
            return ResponseEntity.badRequest().body("Invalid product or invalid user");
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(
            @RequestHeader("X-User-ID") String userId){
        return ResponseEntity.ok(cartService.getCart(userId));
           // return ResponseEntity.badRequest().body("Invalid product or less quantity or invalid user");
        //return ResponseEntity.noContent().build();
    }

}
