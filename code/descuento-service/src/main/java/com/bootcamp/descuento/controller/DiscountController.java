package com.bootcamp.descuento.controller;

import com.bootcamp.descuento.api.ApiApi;
import com.bootcamp.descuento.model.DiscountResponse;
import com.bootcamp.descuento.repository.DiscountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscountController implements ApiApi {

    private final DiscountRepository repository;

    public DiscountController(DiscountRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<DiscountResponse> getDiscountByProductId(Long productId) {
        return repository.findById(productId)
                .map(entity -> {
                    DiscountResponse response = new DiscountResponse();
                    response.setProductId(entity.getProductId());
                    response.setPercentage(entity.getPercentage());
                    response.setDescription(entity.getDescription());
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}