package com.bootcamp.productos.controller;

import com.bootcamp.productos.api.ApiApi;
import com.bootcamp.productos.model.ProductDetailResponse;
import com.bootcamp.productos.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
public class ProductController implements ApiApi {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<ProductDetailResponse> getProductById(Long id) {
        try {
            return ResponseEntity.ok(productService.getProductDetails(id));
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.notFound().build();
        }
    // Otros errores (500/502) se propagan y quedan en los logs
    }
}