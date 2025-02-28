package com.jeyofdev.yellow_berry.util;

import com.github.javafaker.Faker;
import com.jeyofdev.yellow_berry.core.enums.StockEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;
import com.jeyofdev.yellow_berry.domain.brand.*;
import com.jeyofdev.yellow_berry.domain.brand.dto.SaveBrandDTO;
import com.jeyofdev.yellow_berry.domain.category.Category;
import com.jeyofdev.yellow_berry.domain.category.CategoryController;
import com.jeyofdev.yellow_berry.domain.category.CategoryRepository;
import com.jeyofdev.yellow_berry.domain.category.dto.SaveCategoryDTO;
import com.jeyofdev.yellow_berry.domain.product.ProductController;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import com.jeyofdev.yellow_berry.domain.product.dto.SaveProductDTO;
import com.jeyofdev.yellow_berry.domain.tag.TagController;
import com.jeyofdev.yellow_berry.domain.tag.TagRepository;
import com.jeyofdev.yellow_berry.domain.tag.dto.SaveTagDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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

    private final ProductRepository productRepository;
    private final ProductController productController;

    private final Faker faker = new Faker();
    private final Random random = new Random();

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
        this.createFakeProducts();
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

    @Transactional
    private void createFakeProducts() {
        List<Brand> brands = brandRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

        IntStream.range(0, 10).forEach(i -> {
            String productName;
            do {
                productName = faker.commerce().productName();
            } while (productRepository.existsByName(productName));

            int rating = faker.options().option(1, 2, 3, 4, 5);
            double price = faker.number().randomDouble(2, 10, 500);
            double discount = faker.number().randomDouble(2, 0, 50);
            double priceDiscount = price - (price * discount / 100);
            StockEnum stock = StockEnum.IN_STOCK;
            WeightEnum weight = WeightEnum.GRAM_250;
            UUID brandId = brands.get(random.nextInt(brands.size())).getId();
            UUID categoryId = categories.get(random.nextInt(categories.size())).getId();

            SaveProductDTO saveProductDTO = new SaveProductDTO(
                    productName,
                    rating,
                    price,
                    priceDiscount,
                    discount,
                    stock,
                    weight,
                    List.of(), // Tags
                    List.of(categoryId),
                    List.of(), // Comments
                    List.of(), // Wishlist
                    List.of(), // Cart
                    brandId
            );

            productController.saveProduct(saveProductDTO);
        });
    }
}
