package com.jeyofdev.yellow_berry.domain.productDetails;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductDetailsService extends AbstractDomainService<ProductDetails, ProductDetailsRepository> {
    private final ProductDetailsRepository productDetailsRepository;
    private final ProductService productService;

    @Autowired
    public ProductDetailsService(ProductDetailsRepository productDetailsRepository, ProductService productService) {
        super(productDetailsRepository, "Product details");
        this.productDetailsRepository = productDetailsRepository;
        this.productService = productService;
    }

    public ProductDetails save(UUID productId, ProductDetails productDetails) {
        Product product = productService.findById(productId);
        productDetails.setProduct(product);
        product.setProductDetails(productDetails);

        return productDetailsRepository.save(productDetails);
    }

    public ProductDetails updateById(UUID productDetailsId, ProductDetails updatedProductDetails) {
        ProductDetails existingProductDetails = findById(productDetailsId);
        existingProductDetails.setDescription(updatedProductDetails.getDescription() != null ? updatedProductDetails.getDescription() : existingProductDetails.getDescription());
        existingProductDetails.setSeller(updatedProductDetails.getSeller() != null ? updatedProductDetails.getSeller() : existingProductDetails.getSeller());
        existingProductDetails.setService(updatedProductDetails.getService() != null ? updatedProductDetails.getService() : existingProductDetails.getService());

        return productDetailsRepository.save(existingProductDetails);
    }

    public String deleteById(UUID productDetails) {
        findById(productDetails);
        productDetailsRepository.deleteById(productDetails);

        return ConfirmMessage.PRODUCT_DETAILS_DELETE;
    }
}
