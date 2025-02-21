package com.jeyofdev.yellow_berry.domain.productInformation;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductService;
import com.jeyofdev.yellow_berry.domain.productDetails.ProductDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductInformationService extends AbstractDomainService<ProductInformation, ProductInformationRepository> {
    private final ProductInformationRepository productInformationRepository;
    private final ProductService productService;

    @Autowired
    public ProductInformationService(ProductInformationRepository productInformationRepository, ProductService productService) {
        super(productInformationRepository, "Product information");
        this.productInformationRepository = productInformationRepository;
        this.productService = productService;
    }

    public ProductInformation save(UUID productId, ProductInformation productInformation) {
        Product product = productService.findById(productId);
        productInformation.setProduct(product);

        return productInformationRepository.save(productInformation);
    }

    public ProductInformation updateById(UUID productInformationId, ProductInformation updatedProductInformation) {
        ProductInformation existingProductInformation = findById(productInformationId);

        existingProductInformation.setWeight(updatedProductInformation.getWeight() != null ? updatedProductInformation.getWeight() : existingProductInformation.getWeight());
        existingProductInformation.setDimension(updatedProductInformation.getDimension() != null ? updatedProductInformation.getDimension() : existingProductInformation.getDimension());
        existingProductInformation.setColor(updatedProductInformation.getColor() != null ? updatedProductInformation.getColor() : existingProductInformation.getColor());
        existingProductInformation.setBrand(updatedProductInformation.getBrand() != null ? updatedProductInformation.getBrand() : existingProductInformation.getBrand());
        existingProductInformation.setQuantity(updatedProductInformation.getQuantity() != null ? updatedProductInformation.getQuantity() : existingProductInformation.getQuantity());

        return productInformationRepository.save(existingProductInformation);
    }

    public String deleteById(UUID productInformationId) {
        findById(productInformationId);
        productInformationRepository.deleteById(productInformationId);

        return ConfirmMessage.PRODUCT_INFORMATION_DELETE;
    }
}
