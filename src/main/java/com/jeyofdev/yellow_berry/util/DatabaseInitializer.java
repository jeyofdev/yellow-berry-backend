package com.jeyofdev.yellow_berry.util;

import com.github.javafaker.Faker;
import com.jeyofdev.yellow_berry.auth.AuthServiceImpl;
import com.jeyofdev.yellow_berry.auth_user.AuthUserRepository;
import com.jeyofdev.yellow_berry.core.constant.Url;
import com.jeyofdev.yellow_berry.core.enums.*;
import com.jeyofdev.yellow_berry.domain.about.AboutController;
import com.jeyofdev.yellow_berry.domain.about.AboutRepository;
import com.jeyofdev.yellow_berry.domain.about.dto.SaveAboutDTO;
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
import com.jeyofdev.yellow_berry.domain.faq.FaqController;
import com.jeyofdev.yellow_berry.domain.faq.FaqRepository;
import com.jeyofdev.yellow_berry.domain.faq.dto.SaveFaqDTO;
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
import com.jeyofdev.yellow_berry.domain.profile.dto.ProfileDTO;
import com.jeyofdev.yellow_berry.domain.profile.dto.SaveProfileDTO;
import com.jeyofdev.yellow_berry.domain.service.ServiceController;
import com.jeyofdev.yellow_berry.domain.service.ServiceRepository;
import com.jeyofdev.yellow_berry.domain.service.dto.SaveServiceDTO;
import com.jeyofdev.yellow_berry.domain.tag.Tag;
import com.jeyofdev.yellow_berry.domain.tag.TagController;
import com.jeyofdev.yellow_berry.domain.tag.TagRepository;
import com.jeyofdev.yellow_berry.domain.tag.dto.SaveTagDTO;
import com.jeyofdev.yellow_berry.domain.team_member.TeamMemberController;
import com.jeyofdev.yellow_berry.domain.team_member.TeamMemberRepository;
import com.jeyofdev.yellow_berry.domain.team_member.dto.SaveTeamMemberDTO;
import com.jeyofdev.yellow_berry.domain.testimonial.TestimonialController;
import com.jeyofdev.yellow_berry.domain.testimonial.TestimonialRepository;
import com.jeyofdev.yellow_berry.domain.testimonial.dto.SaveTestimonialDTO;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.SaveWishlistDTO;
import com.jeyofdev.yellow_berry.domain.wishlist.dto.WishlistDTO;
import com.jeyofdev.yellow_berry.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
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

    private final ServiceRepository serviceRepository;
    private final ServiceController serviceController;

    private final AboutRepository aboutRepository;
    private final AboutController aboutController;

    private final TestimonialRepository testimonialRepository;
    private final TestimonialController testimonialController;

    private final FaqRepository faqRepository;
    private final FaqController faqController;

    private final TeamMemberRepository teamMemberRepository;
    private final TeamMemberController teamMemberController;

    private final ProductRepository productRepository;
    private final ProductController productController;

    private final ProductDetailsRepository productDetailsRepository;
    private final ProductDetailsMapper productDetailsMapper;

    private final ProductInformationRepository productInformationRepository;
    private final ProductInformationMapper productInformationMapper;

    private final AuthUserRepository authUserRepository;
    private final AuthServiceImpl authServiceImpl;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    private final FakeData fakeData;
    private final JwtService jwtService;

    @Override
    public void run(String... args) throws Exception {
        this.createDatas();
    }

    private void createDatas() throws IOException {
        this.createFakeServices();
        this.createFakeTestimonials();
        this.createFakeTeamMember();
        this.createFakeAbout();
        this.createFakeFaq();
        this.createFakeBrands();
        this.createFakeCategories();
        this.createFakeTags();
        this.createFakeProductsWithDetailsAndInformations();
        this.createUsers();
        this.createFakeProfiles();
    }

    private void createFakeServices() {
        fakeData.generate(
                (int) serviceRepository.count(),
                () -> List.of(faker.name().title()),
                data -> serviceRepository.existsByName(data.getFirst()),
                4,
                data -> {
                    SaveServiceDTO saveServiceDTO = new SaveServiceDTO(data.getFirst(), faker.lorem().sentence(15));
                    serviceController.saveService(saveServiceDTO);
                }
        );
    }

    private void createFakeTestimonials() {
        fakeData.generate(
                (int) testimonialRepository.count(),
                () -> {
                    String firstName, lastName;
                    do {
                        firstName = faker.name().firstName();
                    } while (firstName.length() < 3 || firstName.length() > 30);

                    do {
                        lastName = faker.name().lastName();
                    } while (lastName.length() < 3 || lastName.length() > 80);

                    return List.of(firstName, lastName);
                },
                data -> testimonialRepository.existsByFirstnameAndLastname(data.getFirst(), data.getLast()),
                5,
                data -> {
                    SaveTestimonialDTO saveTestimonialDTO = new SaveTestimonialDTO(data.getFirst(), data.getLast(), JobEnum.getRandomJob(), faker.lorem().sentence(15));
                    testimonialController.saveTestimonial(saveTestimonialDTO);
                }
        );
    }

    private void createFakeTeamMember() {
        if (teamMemberRepository.count() == 0) {
            IntStream.range(0, 6).forEach(i -> {
                String teamMemberFirstname;
                String teamMemberLastname;
                String teamMemberTwitter;
                String teamMemberInstagram;
                String teamMemberLinkedin;

                do {
                    do {
                        teamMemberFirstname = faker.name().firstName();
                        teamMemberLastname = faker.name().lastName();
                    } while (teamMemberFirstname.length() < 3 || teamMemberLastname.length() < 3);

                    teamMemberTwitter = generateSocialUsername(teamMemberFirstname);
                    teamMemberInstagram = generateSocialUsername(teamMemberFirstname);
                    teamMemberLinkedin = generateSocialUsername(teamMemberFirstname);

                } while (
                        teamMemberRepository.existsByFirstnameAndLastname(teamMemberFirstname, teamMemberLastname) ||
                                teamMemberRepository.existsByTwitter(teamMemberTwitter) ||
                                teamMemberRepository.existsByInstagram(teamMemberInstagram) ||
                                teamMemberRepository.existsByLinkedin(teamMemberLinkedin)
                );

                SaveTeamMemberDTO saveTeamMemberDTO = new SaveTeamMemberDTO(teamMemberFirstname, teamMemberLastname, JobEnum.getRandomJob(), teamMemberTwitter, teamMemberInstagram, teamMemberLinkedin);
                teamMemberController.saveTeamMember(saveTeamMemberDTO);
            });
        }
    }

    private void createFakeAbout() {
        if (aboutRepository.count() == 0) {
            SaveAboutDTO saveAboutDTO = new SaveAboutDTO("About the BlueBerry", "Farm-fresh Goodness, just a click Away.", faker.lorem().sentence(40));
            aboutController.saveAbout(saveAboutDTO);
        }
    }

    private void createFakeFaq() {
        fakeData.generate(
                (int) faqRepository.count(),
                () -> List.of(faker.lorem().sentence()),
                data -> faqRepository.existsByQuestion(data.getFirst()),
                5,
                data -> {
                    SaveFaqDTO saveFaqDTO = new SaveFaqDTO(data.getFirst(), faker.lorem().paragraph(random.nextInt(31) + 30));
                    faqController.saveFaq(saveFaqDTO);
                }
        );
    }

    private void createFakeBrands() {
        fakeData.generate(
                (int) brandRepository.count(),
                () -> List.of(faker.commerce().department()),
                data -> brandRepository.existsByName(data.getFirst()),
                10,
                data -> {
                    SaveBrandDTO saveBrandDTO = new SaveBrandDTO(data.getFirst(), ColorEnum.getRandomColor());
                    System.out.println(saveBrandDTO);
                    brandController.saveBrand(saveBrandDTO);
                }
        );
    }

    private void createFakeCategories() {
        fakeData.generate(
                (int) categoryRepository.count(),
                () -> List.of(faker.commerce().department()),
                data -> categoryRepository.existsByName(data.getFirst()),
                10,
                data -> {
                    SaveCategoryDTO saveCategoryDTO = new SaveCategoryDTO(data.getFirst());
                    categoryController.saveCategory(saveCategoryDTO);
                }
        );
    }

    private void createFakeTags() {
        fakeData.generate(
                (int) tagRepository.count(),
                () -> List.of(faker.food().fruit()),
                data -> tagRepository.existsByName(data.getFirst()),
                10,
                data -> {
                    SaveTagDTO saveTagDTO = new SaveTagDTO(data.getFirst());
                    tagController.saveTag(saveTagDTO);
                }
        );
    }

    private void createFakeProductsWithDetailsAndInformations() {
        List<Brand> brands = brandRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

        fakeData.generate(
                (int) productRepository.count(),
                () -> List.of(faker.commerce().productName()),
                data -> productRepository.existsByName(data.getFirst()),
                10,
                data -> {
                    String reference = faker.regexify("[A-Z]{2}[A-Z0-9][0-9]{2}");
                    int rating = 1;
                    double price = faker.number().randomDouble(2, 10, 500);
                    int discount = faker.number().numberBetween(1, 99);
                    StockEnum stock = StockEnum.IN_STOCK;
                    UUID brandId = brands.get(random.nextInt(brands.size())).getId();
                    UUID categoryId = categories.get(random.nextInt(categories.size())).getId();

                    List<UUID> tagIds = tagRepository.findAll().stream()
                            .map(Tag::getId)
                            .toList();

                    List<UUID> randomTagIds = new ArrayList<>();
                    int numberOfTags = faker.number().numberBetween(1, 3);
                    for (int i = 0; i < numberOfTags; i++) {
                        int randomIndex = faker.number().numberBetween(0, tagIds.size());
                        randomTagIds.add(tagIds.get(randomIndex));
                    }

                    SaveProductDTO saveProductDTO = new SaveProductDTO(
                            data.getFirst(),
                            reference,
                            rating,
                            price,
                            discount,
                            stock,
                            randomTagIds,
                            List.of(categoryId),
                            List.of(), // Comments
                            List.of(), // Wishlist
                            List.of(), // Cart
                            brandId
                    );
                    productController.saveProduct(saveProductDTO);
                }
        );

        List<Product> products = productRepository.findAll();

        this.createFakeProductDetails(products);
        this.createFakeProductInformations(products);
    }

    private void createFakeProductDetails(List<Product> products) {
        for (Product product : products) {
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
    }

    private void createFakeProductInformations(List<Product> products) {
        for (Product product : products) {
            List<WeightEnum> weightList = WeightEnum.getRandomWeightList(faker.number().numberBetween(2, 10));
            String dimension = "17 × 15 × 3 cm";
            List<ColorEnum> colorList = ColorEnum.getRandomColorList(faker.number().numberBetween(2, 12));
            Integer quantity = faker.number().numberBetween(1, 100);

            SaveProductInformationDTO saveProductInformationDTO = new SaveProductInformationDTO(weightList, dimension, colorList, quantity);

            ProductInformation productInformation = productInformationMapper.mapToEntity(saveProductInformationDTO);
            productInformation.setProduct(product);
            productInformationRepository.save(productInformation);

            product.setProductInformation(productInformation);
            productRepository.save(product);
        }
    }

    private void createUsers() {
        if (authUserRepository.count() == 0) {
            fakeData.registerFakeUser("user@test.fr", "uSer12345*4", RoleEnum.USER);
            fakeData.registerFakeUser("admin@test.fr", "adMin12345*4", RoleEnum.USER);
        }
    }

    private void createFakeProfiles() {
        List<String> authenticatedUsers = loginUsers();
        authenticatedUsers.forEach(this::createFakeProfileForUser);
    }

    private void createFakeProfileForUser(String token) {
        UUID userId = jwtService.extractUserIdFromToken(token);
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
                String.valueOf(33000),
                faker.address().city(),
                faker.address().state(),
                faker.address().state()
        );

        UUID profileId = fakeData.runSaveRequestWithAuthentication(token, saveProfileDTO, Url.getFullBaseUrl() + "/profile/user/" + userId, ProfileDTO.class);

        createFakeCarts(profileId, token);
        createFakeWishlist(profileId, token);
        createFakeComments(profileId, token);
    }

    public void createFakeCarts(UUID profileId, String authenticateToken) {
        SaveCartDTO saveCartDTO = new SaveCartDTO();
        fakeData.runSaveRequestWithAuthentication(authenticateToken, saveCartDTO, Url.getFullBaseUrl() + "/cart/profile/" + profileId, CartDTO.class);
    }

    public void createFakeWishlist(UUID profileId, String authenticateToken) {
        SaveWishlistDTO saveWishlistDTO = new SaveWishlistDTO(faker.name().title());
        fakeData.runSaveRequestWithAuthentication(authenticateToken, saveWishlistDTO, Url.getFullBaseUrl() + "/wishlist/profile/" + profileId, WishlistDTO.class);
    }

    public void createFakeComments(UUID profileId, String authenticateToken) {
        List<Product> productList = productRepository.findAll();

        for (Product product : productList) {
            int numberOfComments = faker.number().numberBetween(5, 10);

            for (int i = 0; i < numberOfComments; i++) {
                Integer commentRating = faker.number().numberBetween(1, 6);
                String commentText = faker.lorem().paragraph();
                UUID productId = product.getId();

                SaveCommentDTO saveCommentDTO = new SaveCommentDTO(commentRating, commentText);

                fakeData.runSaveRequestWithAuthentication(authenticateToken, saveCommentDTO, Url.getFullBaseUrl() + "/comment/product/" + productId + "/profile/" + profileId, CommentDTO.class);
            }
        }
    }

    private List<String> loginUsers() {
        String userToken = fakeData.getAuthenticationToken("user@test.fr", "uSer12345*4");
        String adminToken = fakeData.getAuthenticationToken("admin@test.fr", "adMin12345*4");

        return new ArrayList<>(List.of(userToken, adminToken));
    }

    private String generateSocialUsername(String firstname) {
        int randomNumber = faker.number().numberBetween(10, 9999);
        return firstname.replaceAll("[^a-zA-Z0-9]", "") + randomNumber;
    }
}
