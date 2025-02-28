package com.jeyofdev.yellow_berry.util;

import com.github.javafaker.Faker;
import com.jeyofdev.yellow_berry.domain.brand.*;
import com.jeyofdev.yellow_berry.domain.brand.dto.SaveBrandDTO;
import com.jeyofdev.yellow_berry.domain.category.CategoryController;
import com.jeyofdev.yellow_berry.domain.category.CategoryRepository;
import com.jeyofdev.yellow_berry.domain.category.dto.SaveCategoryDTO;
import com.jeyofdev.yellow_berry.domain.tag.TagController;
import com.jeyofdev.yellow_berry.domain.tag.TagRepository;
import com.jeyofdev.yellow_berry.domain.tag.dto.SaveTagDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final BrandRepository brandRepository;
    private final BrandController brandController;

    private final CategoryRepository categoryRepository;
    private final CategoryController categoryController;

    private final TagRepository tagRepository;
    private final TagController tagController;

    private final Faker faker = new Faker();

    /*public static void initializeDatabase(String jdbcUrl, String user, String password, String dbName) {
        String createDbQuery = MessageFormat.format("CREATE DATABASE {0}", dbName);
        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
             Statement statement = connection.createStatement()) {

            // If the database does not exist, create it
            statement.executeUpdate(createDbQuery);
            System.out.println("Database created successfully!");
        } catch (SQLException e) {
            if (e.getSQLState().equals("42P04")) { // Code error for "Database already exists"
                System.out.println("The database already exists.");
            } else {
                System.err.println(MessageFormat.format("Error creating database : {0}", e.getMessage()));
                throw new RuntimeException(e);
            }
        }
    }*/

    @Override
    public void run(String... args) throws Exception {
        this.createDatas();
    }

    private void createDatas() throws IOException {
        System.out.println("Database initialization started...");
        this.createFakeBrands();
        this.createFakeCategories();
        this.createFakeTags();
    }

    private void createFakeBrands() {
        if (brandRepository.count() == 0) {
            IntStream.range(0, 10).forEach(i -> {
                String brandName;
                do {
                    brandName = faker.company().name();
                } while (brandRepository.existsByName(brandName));

                SaveBrandDTO saveBrandDTO = new SaveBrandDTO(brandName);
                brandController.saveBrand(saveBrandDTO);
            });
        }
    }

    private void createFakeCategories() {
        if (categoryRepository.count() == 0) {
            IntStream.range(0, 10).forEach(i -> {
                String categoryName;
                do {
                    categoryName = faker.commerce().department();
                } while (categoryRepository.existsByName(categoryName));

                SaveCategoryDTO saveCategoryDTO = new SaveCategoryDTO(categoryName);
                categoryController.saveCategory(saveCategoryDTO);
            });
        }
    }

    private void createFakeTags() {
        if (tagRepository.count() == 0) {
            IntStream.range(0, 10).forEach(i -> {
                String tagName;
                do {
                    tagName = faker.food().fruit();
                } while (tagRepository.existsByName(tagName));

                SaveTagDTO saveTagDTO = new SaveTagDTO(tagName);
                tagController.saveTag(saveTagDTO);
            });
        }
    }
}
