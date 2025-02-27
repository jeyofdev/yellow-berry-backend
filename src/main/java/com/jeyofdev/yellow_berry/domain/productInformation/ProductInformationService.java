package com.jeyofdev.yellow_berry.domain.productInformation;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductService;
import com.jeyofdev.yellow_berry.exception.AlreadyAssociatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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

        if (product.getProductInformation() != null) {
            throw new AlreadyAssociatedException(MessageFormat.format(ErrorMessage.ALREADY_ASSOCIATED, "product", "informations"));
        }

        productInformation.setProduct(product);
        product.setProductInformation(productInformation);

        return productInformationRepository.save(productInformation);
    }

    public ProductInformation updateById(UUID productInformationId, ProductInformation updatedProductInformation) {
        ProductInformation existingProductInformation = findById(productInformationId);

        existingProductInformation.setWeight(updatedProductInformation.getWeight() != null ? updatedProductInformation.getWeight() : existingProductInformation.getWeight());
        existingProductInformation.setDimension(updatedProductInformation.getDimension() != null ? updatedProductInformation.getDimension() : existingProductInformation.getDimension());
        existingProductInformation.setColor(updatedProductInformation.getColor() != null ? updatedProductInformation.getColor() : existingProductInformation.getColor());
        existingProductInformation.setQuantity(updatedProductInformation.getQuantity() != null ? updatedProductInformation.getQuantity() : existingProductInformation.getQuantity());

        return productInformationRepository.save(existingProductInformation);
    }

    public String deleteById(UUID productInformationId) {
        findById(productInformationId);
        productInformationRepository.deleteById(productInformationId);

        return MessageFormat.format(ConfirmMessage.CONFIRM_DELETE_PLURAL, "product informations");
    }
}
