package com.jeyofdev.yellow_berry.util;

import com.github.javafaker.Faker;
import com.jeyofdev.yellow_berry.auth.AuthServiceImpl;
import com.jeyofdev.yellow_berry.auth.model.AuthResponse;
import com.jeyofdev.yellow_berry.auth.model.LoginRequest;
import com.jeyofdev.yellow_berry.auth.model.RegisterRequest;
import com.jeyofdev.yellow_berry.auth_user.AuthUserRepository;
import com.jeyofdev.yellow_berry.core.enums.ColorEnum;
import com.jeyofdev.yellow_berry.core.enums.RoleEnum;
import com.jeyofdev.yellow_berry.core.enums.StockEnum;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;
import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.brand.Brand;
import com.jeyofdev.yellow_berry.domain.brand.BrandController;
import com.jeyofdev.yellow_berry.domain.brand.BrandRepository;
import com.jeyofdev.yellow_berry.domain.brand.dto.SaveBrandDTO;
import com.jeyofdev.yellow_berry.domain.cart.dto.CartDTO;
import com.jeyofdev.yellow_berry.domain.cart.dto.SaveCartDTO;
import com.jeyofdev.yellow_berry.domain.category.Category;
import com.jeyofdev.yellow_berry.domain.category.CategoryController;
import com.jeyofdev.yellow_berry.domain.category.CategoryRepository;
import com.jeyofdev.yellow_berry.domain.category.dto.SaveCategoryDTO;
import com.jeyofdev.yellow_berry.domain.comment.dto.CommentDTO;
import com.jeyofdev.yellow_berry.domain.comment.dto.SaveCommentDTO;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductController;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import com.jeyofdev.yellow_berry.domain.product.dto.SaveProductDTO;
import com.jeyofdev.yellow_berry.domain.productDetails.ProductDetails;
import com.jeyofdev.yellow_berry.domain.productDetails.ProductDetailsMapper;
import com.jeyofdev.yellow_berry.domain.productDetails.ProductDetailsRepository;
import com.jeyofdev.yellow_berry.domain.productDetails.dto.SaveProductDetailsDTO;
import com.jeyofdev.yellow_berry.domain.productInformation.ProductInformation;
import com.jeyofdev.yellow_berry.domain.productInformation.ProductInformationMapper;
import com.jeyofdev.yellow_berry.domain.productInformation.ProductInformationRepository;
import com.jeyofdev.yellow_berry.domain.productInformation.dto.SaveProductInformationDTO;
import com.jeyofdev.yellow_berry.domain.profile.ProfileMapper;
import com.jeyofdev.yellow_berry.domain.profile.ProfileService;
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;
import com.jeyofdev.yellow_berry.domain.profile.dto.SaveProfileDTO;
import com.jeyofdev.yellow_berry.domain.tag.TagController;
import com.jeyofdev.yellow_berry.domain.tag.TagRepository;
import com.jeyofdev.yellow_berry.domain.tag.dto.SaveTagDTO;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.SaveWishlistDTO;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.WishlistDTO;
import com.jeyofdev.yellow_berry.security.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
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

    private final ProductDetailsRepository productDetailsRepository;
    private final ProductDetailsMapper productDetailsMapper;

    private final ProductInformationRepository productInformationRepository;
    private final ProductInformationMapper productInformationMapper;

    private final AuthUserRepository authUserRepository;
    private final AuthServiceImpl authServiceImpl;

    private final ProfileMapper profileMapper;
    private final ProfileService profileService;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    private final JwtService jwtService;

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
        this.createFakeProductDetails();
        this.createFakeProductInformations();
        this.createUsers();
        this.createProfiles();
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
            double discount = faker.number().randomDouble(2, 1, 50);
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

    public void createFakeProductDetails() {
        List<Product> products = productRepository.findAll();

        products.forEach(product -> {
            if (product.getProductDetails() == null) {
                String description = faker.lorem().paragraph();
                String seller = faker.company().name();
                String service = faker.company().industry();

                SaveProductDetailsDTO saveProductDetailsDTO = new SaveProductDetailsDTO(description, seller, service);
                ProductDetails productDetails = productDetailsMapper.mapToEntity(saveProductDetailsDTO);
                productDetails.setProduct(product);
                productDetailsRepository.save(productDetails);

                product.setProductDetails(productDetails);
                productRepository.save(product);
            }
        });
    }

    public void createFakeProductInformations() {
        List<Product> products = productRepository.findAll();

        products.forEach(product -> {
            if (product.getProductInformation() == null) {
                WeightEnum weight = faker.options().option(WeightEnum.class);
                String dimension = "17 × 15 × 3 cm";
                ColorEnum color = faker.options().option(ColorEnum.class);
                Integer quantity = faker.number().numberBetween(1, 100);

                SaveProductInformationDTO saveProductInformationDTO = new SaveProductInformationDTO(weight, dimension, color, quantity);
                ProductInformation productInformation = productInformationMapper.mapToEntity(saveProductInformationDTO);
                productInformation.setProduct(product);
                productInformationRepository.save(productInformation);

                product.setProductInformation(productInformation);
                productRepository.save(product);
            }
        });
    }

    public void createUsers() {
        if (authUserRepository.count() == 0) {
            RegisterRequest user = new RegisterRequest("user@test.fr", "uSer12345*4", RoleEnum.USER.toString());
            RegisterRequest admin = new RegisterRequest("admin@test.fr", "adMin12345*4", RoleEnum.USER.toString());

            authServiceImpl.register(user, new BeanPropertyBindingResult(user, "user"));
            authServiceImpl.register(admin, new BeanPropertyBindingResult(admin, "admin"));
        }
    }

    public void createProfiles() {
        List<String> authenticatedUsers = loginUsers();
        authenticatedUsers.forEach(this::createProfileForUser);
    }

    public void createProfileForUser(String token) {
        UUID userId = extractUserIdFromToken(token);
        String phoneNumber = String.format("(+33) 1 %02d %02d %02d %02d",
                faker.number().numberBetween(10, 99),
                faker.number().numberBetween(10, 99),
                faker.number().numberBetween(10, 99),
                faker.number().numberBetween(10, 99)
        );

        SaveProfileDTO saveProfileDTO = new SaveProfileDTO(
                faker.name().firstName(),
                faker.name().lastName(),
                phoneNumber,
                faker.address().streetAddress(),
                faker.address().state(),
                faker.address().city(),
                "75000",
                faker.address().city()
        );

        UUID profileId = sendCreationRequest(token, saveProfileDTO, "http://localhost:8080/api/v1/profile/user/" + userId, ProfileDTO.class);

        createCart(profileId, token);
        createWishlist(profileId, token);
        createComments(profileId, token);

    }

    public void createCart(UUID profileId, String authenticateToken) {
        SaveCartDTO saveCartDTO = new SaveCartDTO();
        sendCreationRequest(authenticateToken, saveCartDTO, "http://localhost:8080/api/v1/cart/profile/" + profileId, CartDTO.class);

    }

    public void createWishlist(UUID profileId, String authenticateToken) {
        SaveWishlistDTO saveWishlistDTO = new SaveWishlistDTO(faker.name().title());
        sendCreationRequest(authenticateToken, saveWishlistDTO, "http://localhost:8080/api/v1/wishlist/profile/" + profileId, WishlistDTO.class);
    }

    public void createComments(UUID profileId, String authenticateToken) {
        List<Product> productList = productRepository.findAll();

        productList.forEach(product -> {
            int numberOfComments = faker.number().numberBetween(1, 4);

            for (int i = 0; i < numberOfComments; i++) {
                Integer commentRating = faker.number().numberBetween(1, 6);
                String commentText = faker.lorem().paragraph();
                UUID productId = product.getId();

                SaveCommentDTO saveCommentDTO = new SaveCommentDTO(commentRating, commentText);

                sendCreationRequest(authenticateToken, saveCommentDTO, "http://localhost:8080/api/v1/comment/product/" + productId + "/profile/" + profileId, CommentDTO.class);
            }
        });
    }

    public List<String> loginUsers() {
        AuthResponse user = authServiceImpl.login(
                new LoginRequest("user@test.fr", "uSer12345*4"),
                null
        );

        AuthResponse admin = authServiceImpl.login(
                new LoginRequest("admin@test.fr", "adMin12345*4"),
                null
        );

        List<String> authenticatedUsers = new ArrayList<>();
        authenticatedUsers.add(user.getToken());
        authenticatedUsers.add(admin.getToken());

        return authenticatedUsers;
    }

    public UUID extractUserIdFromToken(String token) {
        Claims claims = jwtService.extractAllClaims(token);
        return UUID.fromString(claims.get("id").toString());
    }

    private <T, R> UUID sendCreationRequest(String token, T requestBody, String url, Class<R> responseType) {
        RestTemplate restTemplate = new RestTemplate();

        // configure headers with the token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        // request and response
        HttpEntity<T> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<DomainSuccessResponse<R>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        // if request is success
        if (response.getStatusCode().is2xxSuccessful()) {
            R result = response.getBody().getResult();

            if (result == null) {
                throw new RuntimeException("The response does not contain any results.");
            }

            // extract id of response
            if (result instanceof Map<?, ?>) {
                @SuppressWarnings("unchecked")
                Map<String, Object> resultMap = (Map<String, Object>) result;

                if (resultMap.containsKey("id")) {
                    return UUID.fromString(resultMap.get("id").toString());
                }
            }

            if (result instanceof ProfileDTO profileDTO) {
                return profileDTO.id();
            }

            throw new RuntimeException("The response does not contain a valid ID..");
        }

        throw new RuntimeException("Failed to create entity");
    }
}
