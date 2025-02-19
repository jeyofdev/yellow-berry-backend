package com.jeyofdev.yellow_berry.domain.brand;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BrandService extends AbstractDomainService<Brand, BrandRepository> {
    private final BrandRepository brandRepository;

    @Autowired
    public BrandService(BrandRepository brandRepository) {
        super(brandRepository, "Brand");
        this.brandRepository = brandRepository;
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
        findById(brandId);
        brandRepository.deleteById(brandId);

        return ConfirmMessage.BRAND_DELETE;
    }
}
