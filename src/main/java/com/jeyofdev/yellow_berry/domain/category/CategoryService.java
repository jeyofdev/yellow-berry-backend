package com.jeyofdev.yellow_berry.domain.category;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService extends AbstractDomainService<Category, CategoryRepository> {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository, "Category");
        this.categoryRepository = categoryRepository;
    }

    public Category updateById(UUID categoryId, Category updatedCategory) {
        Category existingCategory = findById(categoryId);
        Category existingCategoryUpdated = Category.builder()
                .id(categoryId)
                .name(updatedCategory.getName() != null ? updatedCategory.getName() : existingCategory.getName())
                .build();

        return categoryRepository.save(existingCategoryUpdated);
    }

    public String deleteById(UUID categoryId) {
        findById(categoryId);
        categoryRepository.deleteById(categoryId);

        return ConfirmMessage.CATEGORY_DELETE;
    }
}
