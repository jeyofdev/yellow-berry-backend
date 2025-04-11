package com.jeyofdev.yellow_berry.domain.category;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.category.dto.CategoryDTO;
import com.jeyofdev.yellow_berry.domain.category.dto.CategoryPreviewDTO;
import com.jeyofdev.yellow_berry.domain.category.dto.SaveCategoryDTO;
import com.jeyofdev.yellow_berry.domain.product.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<CategoryDTO>>> findAllCategories() {
        List<Category> categoryList = categoryService.findAll();
        List<CategoryDTO> categoryDTOList = categoryList.stream().map(category -> categoryMapper.mapFromEntity(category, productMapper)).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, categoryDTOList);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<DomainSuccessResponse<CategoryDTO>> findCategoryById(@PathVariable("categoryId") UUID categoryId) {
        Category category = categoryService.findById(categoryId);
        CategoryDTO categoryDTO = categoryMapper.mapFromEntity(category, productMapper);

        return DomainSuccessResponse.get(HttpStatus.OK, categoryDTO);
    }

    @PostMapping
    public ResponseEntity<DomainSuccessResponse<CategoryPreviewDTO>> saveCategory(@RequestBody SaveCategoryDTO saveCategoryDTO) {
        Category category = categoryMapper.mapToEntity(saveCategoryDTO);
        Category newCategory = categoryService.save(category);
        CategoryPreviewDTO newCategoryPreviewDTO = categoryMapper.mapFromEntityPreview(newCategory);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newCategoryPreviewDTO);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<DomainSuccessResponse<CategoryDTO>> updateCategoryById(
            @PathVariable("categoryId") UUID categoryId,
            @RequestBody SaveCategoryDTO saveCategoryDTO
    ) {
        Category category = categoryMapper.mapToEntity(saveCategoryDTO);
        Category updateCategory = categoryService.updateById(categoryId, category);
        CategoryDTO updateCategoryDTO = categoryMapper.mapFromEntity(updateCategory, productMapper);

        return DomainSuccessResponse.get(HttpStatus.OK, updateCategoryDTO);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteCategoryById(@PathVariable("categoryId") UUID categoryId) {
        String message = categoryService.deleteById(categoryId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);

    }
}
