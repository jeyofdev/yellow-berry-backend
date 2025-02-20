package com.jeyofdev.yellow_berry.domain.product;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.tag.Tag;
import com.jeyofdev.yellow_berry.domain.tag.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService extends AbstractDomainService<Product, ProductRepository> {
    private final ProductRepository productRepository;
    private final TagRepository tagRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, TagRepository tagRepository) {
        super(productRepository, "Product");
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
    }

    public Product save(Product product) {
        if (product.getTagList() != null && !product.getTagList().isEmpty()) {
            List<UUID> tagIds = product.getTagList().stream().map(Tag::getId).toList();
            product.setTagList(tagRepository.findAllById(tagIds));
        }

        return productRepository.save(product);
    }

    public Product updateById(UUID productId, Product updatedProduct) {
        Product existingProduct = findById(productId);

        List<Tag> updatedTags;
        if (updatedProduct.getTagList() != null && !updatedProduct.getTagList().isEmpty()) {
            updatedTags = tagRepository.findAllById(
                updatedProduct.getTagList().stream().map(Tag::getId).toList()
            );
        } else {
            updatedTags = existingProduct.getTagList();
        }

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
                .tagList(updatedTags)
                .build();

        return productRepository.save(existingProductUpdated);
    }

    public String deleteById(UUID productId) {
        findById(productId);
        productRepository.deleteById(productId);

        return ConfirmMessage.PRODUCT_DELETE;
    }
}
