package com.ecom.product.controller;

import com.ecom.product.dto.ProductRequest;
import com.ecom.product.dto.ProductResponse;
import com.ecom.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;


    @GetMapping("/simulateFailure")
    public ResponseEntity<String> simulateFailure(
            @RequestParam(defaultValue = "false") boolean fail){
        if(fail) {
            throw new RuntimeException("Simulate failed for testing");
        }
        return ResponseEntity.ok("productService is ok");
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return new  ResponseEntity<ProductResponse>(productService.createProduct(productRequest),
                                                        HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable String productId){
        return productService.getProductById(productId)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PutMapping ("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest productRequest){
        /*return new  ResponseEntity<ProductResponse>(productService.updateProduct(id,productRequest),
                HttpStatus.OK);*/
        return productService.updateProduct(id,productRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping ("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id){
          boolean deleted =  productService.deleteProduct(id);
          return deleted? ResponseEntity.noContent().build():ResponseEntity.notFound().build();
        //return ResponseEntity.noContent().build();

    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProduct(@RequestParam String keyword){
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }

}
