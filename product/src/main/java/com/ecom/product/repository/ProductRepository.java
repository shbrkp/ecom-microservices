package com.ecom.product.repository;

import com.ecom.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByActiveTrue();

    // where p.active= true and p.stockQuantity>0
    //@Query("SELECT p FROM products p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    @Query("SELECT p FROM products p WHERE p.stockQuantity>0 AND p.active = true AND p.name ILIKE %:keyword%")
    List<Product> searchProduct(@Param("keyword") String keyword);
}
