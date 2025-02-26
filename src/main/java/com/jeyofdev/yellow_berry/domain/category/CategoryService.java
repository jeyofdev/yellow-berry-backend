package com.jeyofdev.yellow_berry.domain.category;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.domain.brand.Brand;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import com.jeyofdev.yellow_berry.domain.tag.Tag;
import com.jeyofdev.yellow_berry.exception.AlreadyTakenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService extends AbstractDomainService<Category, CategoryRepository> {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        super(categoryRepository, "Category");
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> getCategoriesByIds(List<UUID> categoryIds) {
        return categoryIds == null || categoryIds.isEmpty() ? List.of() : categoryRepository.findAllById(categoryIds);
    }

    @Override
    public Category save(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new AlreadyTakenException(MessageFormat.format(ErrorMessage.ALREADY_TAKEN, entityName, "name", category.getName()));
        }

        return categoryRepository.save(category);
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
        Category category = findById(categoryId);
        List<Product> productList = productRepository.findByCategory(category);

        for (Product product : productList) {
            product.getCategoryList().remove(category);
            productRepository.save(product);
        }

        findById(categoryId);
        categoryRepository.deleteById(categoryId);

        return ConfirmMessage.CATEGORY_DELETE;
    }
}
