package com.jeyofdev.yellow_berry.domain.product;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService extends AbstractDomainService<Product, ProductRepository> {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        super(productRepository, "Product");
        this.productRepository = productRepository;
    }

    public Product updateById(UUID productId, Product updatedProduct) {
        Product existingProduct = findById(productId);
        Product existingProductUpdated = Product.builder()
                .id(productId)
                .name(updatedProduct.getName() != null ? updatedProduct.getName() : existingProduct.getName())
                .rating(updatedProduct.getRating() != null ? updatedProduct.getRating() : existingProduct.getRating())
                .description(updatedProduct.getDescription() != null ? updatedProduct.getDescription() : existingProduct.getDescription())
                .price(updatedProduct.getPrice() != null ? updatedProduct.getPrice() : existingProduct.getPrice())
                .priceDiscount(updatedProduct.getPriceDiscount() != null ? updatedProduct.getPriceDiscount() : existingProduct.getPriceDiscount())
                .discount(updatedProduct.getDiscount() != null ? updatedProduct.getDiscount() : existingProduct.getDiscount())
                .stock(updatedProduct.getStock() != null ? updatedProduct.getStock() : existingProduct.getStock())
                .weight(updatedProduct.getWeight() != null ? updatedProduct.getWeight() : existingProduct.getWeight())
                .build();

        return productRepository.save(existingProductUpdated);
    }

    public String deleteById(UUID productId) {
        findById(productId);
        productRepository.deleteById(productId);

        return ConfirmMessage.PRODUCT_DELETE;
    }
}
