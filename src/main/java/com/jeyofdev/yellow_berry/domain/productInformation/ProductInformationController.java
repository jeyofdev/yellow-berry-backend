package com.jeyofdev.yellow_berry.domain.productInformation;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.productDetails.ProductDetails;
import com.jeyofdev.yellow_berry.domain.productDetails.dto.ProductDetailsDTO;
import com.jeyofdev.yellow_berry.domain.productDetails.dto.SaveProductDetailsDTO;
import com.jeyofdev.yellow_berry.domain.productInformation.dto.ProductInformationDTO;
import com.jeyofdev.yellow_berry.domain.productInformation.dto.SaveProductInformationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product_information")
@RequiredArgsConstructor
public class ProductInformationController {
    private final ProductInformationService productInformationService;
    private final ProductInformationMapper productInformationMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<ProductInformationDTO>>> findAllProductInformations() {
        List<ProductInformation> productInformationList = productInformationService.findAll();
        List<ProductInformationDTO> productInformationDTOList = productInformationList.stream().map(productInformationMapper::mapFromEntity).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, productInformationDTOList);
    }

    @GetMapping("/{productInformationId}")
    public ResponseEntity<DomainSuccessResponse<ProductInformationDTO>> findProductInformationById(@PathVariable("productInformationId") UUID productInformationId) {
        ProductInformation productInformation = productInformationService.findById(productInformationId);
        ProductInformationDTO productInformationDTO = productInformationMapper.mapFromEntity(productInformation);

        return DomainSuccessResponse.get(HttpStatus.OK, productInformationDTO);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<DomainSuccessResponse<ProductInformationDTO>> saveProductInformation(
            @RequestBody SaveProductInformationDTO saveProductInformationDTO,
            @PathVariable("productId") UUID productId
    ) {
        ProductInformation productInformation = productInformationMapper.mapToEntity(saveProductInformationDTO);
        ProductInformation newProductInformation = productInformationService.save(productId, productInformation);
        ProductInformationDTO newProductInformationDTO = productInformationMapper.mapFromEntity(newProductInformation);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newProductInformationDTO);
    }

    @PutMapping("/{productInformationId}")
    public ResponseEntity<DomainSuccessResponse<ProductInformationDTO>> updateProductInformationById(
            @PathVariable("productInformationId") UUID productInformationId,
            @RequestBody SaveProductInformationDTO saveProductInformationDTO
    ) {
        ProductInformation productInformation = productInformationMapper.mapToEntity(saveProductInformationDTO);
        ProductInformation updateProductInformation = productInformationService.updateById(productInformationId, productInformation);
        ProductInformationDTO updateProductInformationDTO = productInformationMapper.mapFromEntity(updateProductInformation);

        return DomainSuccessResponse.get(HttpStatus.OK, updateProductInformationDTO);
    }

    @DeleteMapping("/{productInformationId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteProductInformationById(@PathVariable("productInformationId") UUID productInformationId) {
        String message = productInformationService.deleteById(productInformationId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);

    }
}
