package com.bootcamp.productos.service;

import com.bootcamp.productos.client.catalog.model.CatalogResponse;
import com.bootcamp.productos.client.discount.model.DiscountResponse;
import com.bootcamp.productos.model.ProductDetailResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final com.bootcamp.productos.client.catalog.api.DefaultApi catalogApiClient;
    private final com.bootcamp.productos.client.discount.api.DefaultApi discountApiClient;

    public ProductService(
            com.bootcamp.productos.client.catalog.api.DefaultApi catalogApiClient,
            com.bootcamp.productos.client.discount.api.DefaultApi discountApiClient) {
        this.catalogApiClient = catalogApiClient;
        this.discountApiClient = discountApiClient;
    }

    public ProductDetailResponse getProductDetails(Long id) {
        // 1. Llamar al microservicio de catálogo
        CatalogResponse catalog = catalogApiClient.getCatalogByProductId(id);
        
        // 2. Llamar al microservicio de descuento
        Double discountPercentage = 0.0;
        try {
            DiscountResponse discount = discountApiClient.getDiscountByProductId(id);
            if (discount != null && discount.getPercentage() != null) {
                discountPercentage = discount.getPercentage();
            }
        } catch (HttpClientErrorException.NotFound e) {
            discountPercentage = 0.0;  // el producto no tiene descuento activo
        }

        // 3. Consolidar los resultados
        double basePrice = catalog.getBasePrice();
        double finalPrice = basePrice - (basePrice * (discountPercentage / 100.0));

        ProductDetailResponse product = new ProductDetailResponse();
        product.setId(catalog.getProductId());
        product.setName(catalog.getName());
        product.setBasePrice(basePrice);
        product.setDiscountPercentage(discountPercentage);
        product.setFinalPrice(finalPrice);
        product.setStock(catalog.getStock());

        return product;
    }
}