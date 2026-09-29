package com.bootcamp.catalogo.controller;

import com.bootcamp.catalogo.api.ApiApi;
import com.bootcamp.catalogo.model.CatalogResponse;
import com.bootcamp.catalogo.repository.CatalogRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogController implements ApiApi {

    private final CatalogRepository repository;

    public CatalogController(CatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<CatalogResponse> getCatalogByProductId(Long productId) {
        return repository.findById(productId)
                .map(entity -> {
                    CatalogResponse response = new CatalogResponse();
                    response.setProductId(entity.getProductId());
                    response.setName(entity.getName());
                    response.setBasePrice(entity.getBasePrice());
                    response.setStock(entity.getStock());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}