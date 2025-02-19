package com.jeyofdev.yellow_berry.domain.productDetails;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductDetailsService extends AbstractDomainService<ProductDetails, ProductDetailsRepository> {
    private final ProductDetailsRepository productDetailsRepository;

    @Autowired
    public ProductDetailsService(ProductDetailsRepository productDetailsRepository) {
        super(productDetailsRepository, "Product details");
        this.productDetailsRepository = productDetailsRepository;
    }

    public ProductDetails updateById(UUID faqId, ProductDetails updatedProductDetails) {
        ProductDetails existingProductDetails = findById(faqId);
        ProductDetails existingProductDetailsUpdated = ProductDetails.builder()
                .id(faqId)
                .description(updatedProductDetails.getDescription() != null ? updatedProductDetails.getDescription() : existingProductDetails.getDescription())
                .seller(updatedProductDetails.getSeller() != null ? updatedProductDetails.getSeller() : existingProductDetails.getSeller())
                .service(updatedProductDetails.getService() != null ? updatedProductDetails.getService() : existingProductDetails.getService())
                .build();

        return productDetailsRepository.save(existingProductDetailsUpdated);
    }

    public String deleteById(UUID faqId) {
        findById(faqId);
        productDetailsRepository.deleteById(faqId);

        return ConfirmMessage.PRODUCT_DETAILS_DELETE;
    }
}
