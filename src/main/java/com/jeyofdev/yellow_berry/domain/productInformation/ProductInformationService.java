package com.jeyofdev.yellow_berry.domain.productInformation;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductInformationService extends AbstractDomainService<ProductInformation, ProductInformationRepository> {
    private final ProductInformationRepository productInformationRepository;

    @Autowired
    public ProductInformationService(ProductInformationRepository productInformationRepository) {
        super(productInformationRepository, "Product information");
        this.productInformationRepository = productInformationRepository;
    }

    public ProductInformation updateById(UUID productInformationId, ProductInformation updatedProductInformation) {
        ProductInformation existingProductInformation = findById(productInformationId);
        ProductInformation existingProductInformationUpdated = ProductInformation.builder()
                .id(productInformationId)
                .weight(updatedProductInformation.getWeight() != null ? updatedProductInformation.getWeight() : existingProductInformation.getWeight())
                .dimension(updatedProductInformation.getDimension() != null ? updatedProductInformation.getDimension() : existingProductInformation.getDimension())
                .color(updatedProductInformation.getColor() != null ? updatedProductInformation.getColor() : existingProductInformation.getColor())
                .brand(updatedProductInformation.getBrand() != null ? updatedProductInformation.getBrand() : existingProductInformation.getBrand())
                .quantity(updatedProductInformation.getQuantity() != null ? updatedProductInformation.getQuantity() : existingProductInformation.getQuantity())
                .build();

        return productInformationRepository.save(existingProductInformationUpdated);
    }

    public String deleteById(UUID productInformationId) {
        findById(productInformationId);
        productInformationRepository.deleteById(productInformationId);

        return ConfirmMessage.PRODUCT_INFORMATION_DELETE;
    }
}
