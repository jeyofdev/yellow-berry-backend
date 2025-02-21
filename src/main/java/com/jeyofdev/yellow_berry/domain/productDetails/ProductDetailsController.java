package com.jeyofdev.yellow_berry.domain.productDetails;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.productDetails.dto.ProductDetailsDTO;
import com.jeyofdev.yellow_berry.domain.productDetails.dto.SaveProductDetailsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product_details")
@RequiredArgsConstructor
public class ProductDetailsController {
    private final ProductDetailsService productDetailsService;
    private final ProductDetailsMapper productDetailsMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<ProductDetailsDTO>>> findAllProductDetails() {
        List<ProductDetails> productDetailsList = productDetailsService.findAll();
        List<ProductDetailsDTO> productDetailsDTOList = productDetailsList.stream().map(productDetailsMapper::mapFromEntity).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, productDetailsDTOList);
    }

    @GetMapping("/{productDetailsId}")
    public ResponseEntity<DomainSuccessResponse<ProductDetailsDTO>> findProductDetailsById(@PathVariable("productDetailsId") UUID productDetailsId) {
        ProductDetails productDetails = productDetailsService.findById(productDetailsId);
        ProductDetailsDTO productDetailsDTO = productDetailsMapper.mapFromEntity(productDetails);

        return DomainSuccessResponse.get(HttpStatus.OK, productDetailsDTO);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<DomainSuccessResponse<ProductDetailsDTO>> saveProductDetails(
            @PathVariable("productId") UUID productId,
            @RequestBody SaveProductDetailsDTO saveProductDetailsDTO
    ) {
        ProductDetails productDetails = productDetailsMapper.mapToEntity(saveProductDetailsDTO);
        ProductDetails newProductDetails = productDetailsService.save(productId, productDetails);
        ProductDetailsDTO newProductDetailsDTO = productDetailsMapper.mapFromEntity(newProductDetails);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newProductDetailsDTO);
    }

    @PutMapping("/{productDetailsId}")
    public ResponseEntity<DomainSuccessResponse<ProductDetailsDTO>> updateProductDetailsById(
            @PathVariable("productDetailsId") UUID productDetailsId,
            @RequestBody SaveProductDetailsDTO saveProductDetailsDTO
    ) {
        ProductDetails productDetails = productDetailsMapper.mapToEntity(saveProductDetailsDTO);
        ProductDetails updateProductDetails = productDetailsService.updateById(productDetailsId, productDetails);
        ProductDetailsDTO updateProductDetailsDTO = productDetailsMapper.mapFromEntity(updateProductDetails);

        return DomainSuccessResponse.get(HttpStatus.OK, updateProductDetailsDTO);
    }

    @DeleteMapping("/{productDetailsId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteProductDetailsById(@PathVariable("productDetailsId") UUID productDetailsId) {
        String message = productDetailsService.deleteById(productDetailsId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);

    }
}
