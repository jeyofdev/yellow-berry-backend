package com.jeyofdev.yellow_berry.domain.brand;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BrandService extends AbstractDomainService<Brand, BrandRepository> {
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository, ProductRepository productRepository) {
        super(brandRepository, "Brand");
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
    }

    public Brand updateById(UUID brandId, Brand updatedBrand) {
        Brand existingBrand = findById(brandId);
        Brand existingBrandUpdated = Brand.builder()
                .id(brandId)
                .name(updatedBrand.getName() != null ? updatedBrand.getName() : existingBrand.getName())
                .build();

        return brandRepository.save(existingBrandUpdated);
    }

    public String deleteById(UUID brandId) {
        Brand brand = findById(brandId);
        List<Product> productList = productRepository.findByBrand(brand);

        for (Product product : productList) {
            product.setBrand(null);
            productRepository.save(product);
        }

        brandRepository.deleteById(brandId);

        return ConfirmMessage.BRAND_DELETE;
    }
}
