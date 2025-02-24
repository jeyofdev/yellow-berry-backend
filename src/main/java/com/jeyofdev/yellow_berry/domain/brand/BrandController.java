package com.jeyofdev.yellow_berry.domain.brand;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.brand.dto.BrandDTO;
import com.jeyofdev.yellow_berry.domain.brand.dto.BrandPreviewDTO;
import com.jeyofdev.yellow_berry.domain.brand.dto.SaveBrandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;
    private final BrandMapper brandMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<BrandDTO>>> findAllBrands() {
        List<Brand> brandList = brandService.findAll();
        List<BrandDTO> brandDTOList = brandList.stream().map(brandMapper::mapFromEntity).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, brandDTOList);
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<DomainSuccessResponse<BrandDTO>> findBrandById(@PathVariable("brandId") UUID brandId) {
        Brand brand = brandService.findById(brandId);
        BrandDTO brandDTO = brandMapper.mapFromEntity(brand);

        return DomainSuccessResponse.get(HttpStatus.OK, brandDTO);
    }

    @PostMapping
    public ResponseEntity<DomainSuccessResponse<BrandPreviewDTO>> saveBrand(@RequestBody SaveBrandDTO saveBrandDTO) {
        Brand brand = brandMapper.mapToEntity(saveBrandDTO);
        Brand newBrand = brandService.save(brand);
        BrandPreviewDTO newBrandPreviewDTO = brandMapper.mapFromEntityPreview(newBrand);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newBrandPreviewDTO);
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<DomainSuccessResponse<BrandDTO>> updateBrandById(
            @PathVariable("brandId") UUID brandId,
            @RequestBody SaveBrandDTO saveBrandDTO
    ) {
        Brand brand = brandMapper.mapToEntity(saveBrandDTO);
        Brand updateBrand = brandService.updateById(brandId, brand);
        BrandDTO updateBrandDTO = brandMapper.mapFromEntity(updateBrand);

        return DomainSuccessResponse.get(HttpStatus.OK, updateBrandDTO);
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteBrandById(@PathVariable("brandId") UUID brandId) {
        String message = brandService.deleteById(brandId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);
    }
}
