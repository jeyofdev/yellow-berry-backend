package com.jeyofdev.yellow_berry.domain.product;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.cart.Cart;
import com.jeyofdev.yellow_berry.domain.cart.CartService;
import com.jeyofdev.yellow_berry.domain.category.Category;
import com.jeyofdev.yellow_berry.domain.category.CategoryRepository;
import com.jeyofdev.yellow_berry.domain.productDetails.ProductDetails;
import com.jeyofdev.yellow_berry.domain.productDetails.ProductDetailsRepository;
import com.jeyofdev.yellow_berry.domain.tag.Tag;
import com.jeyofdev.yellow_berry.domain.tag.TagRepository;
import com.jeyofdev.yellow_berry.domain.wishlist.WishList;
import com.jeyofdev.yellow_berry.domain.wishlist.WishlistRepository;
import com.jeyofdev.yellow_berry.domain.wishlist.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService extends AbstractDomainService<Product, ProductRepository> {
    private final ProductRepository productRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final WishlistService wishlistService;
    private final CartService cartService;

    @Autowired
    public ProductService(
            ProductRepository productRepository,
            TagRepository tagRepository,
            CategoryRepository categoryRepository,
            WishlistService wishlistService,
            CartService cartService

    ) {
        super(productRepository, "Product");
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
        this.categoryRepository = categoryRepository;
        this.wishlistService = wishlistService;
        this.cartService = cartService;
    }

    public Product save(Product product) {
        if (product.getTagList() != null && !product.getTagList().isEmpty()) {
            List<UUID> tagIds = product.getTagList().stream().map(Tag::getId).toList();
            product.setTagList(tagRepository.findAllById(tagIds));
        }

        if (product.getCategoryList() != null && !product.getCategoryList().isEmpty()) {
            List<UUID> categoryIds = product.getCategoryList().stream().map(Category::getId).toList();
            product.setCategoryList(categoryRepository.findAllById(categoryIds));
        }

        return productRepository.save(product);
    }

    public Product updateById(UUID productId, Product updatedProduct) {
        Product existingProduct = findById(productId);

        List<Tag> updatedTags;
        if (updatedProduct.getTagList() != null && !updatedProduct.getTagList().isEmpty()) {
            updatedTags = tagRepository.findAllById(
                updatedProduct.getTagList().stream().map(Tag::getId).toList()
            );
        } else {
            updatedTags = existingProduct.getTagList();
        }

        List<Category> updatedCategories;
        if (updatedProduct.getCategoryList() != null && !updatedProduct.getCategoryList().isEmpty()) {
            updatedCategories = categoryRepository.findAllById(
                updatedProduct.getCategoryList().stream().map(Category::getId).toList()
            );
        } else {
            updatedCategories = existingProduct.getCategoryList();
        }

        Product existingProductUpdated = Product.builder()
                .id(productId)
                .name(updatedProduct.getName() != null ? updatedProduct.getName() : existingProduct.getName())
                .rating(updatedProduct.getRating() != null ? updatedProduct.getRating() : existingProduct.getRating())
                .description(updatedProduct.getDescription() != null ? updatedProduct.getDescription() : existingProduct.getDescription())
                .price(updatedProduct.getPrice() != null ? updatedProduct.getPrice() : existingProduct.getPrice())
                .priceDiscount(updatedProduct.getPriceDiscount() != null ? updatedProduct.getPriceDiscount() : existingProduct.getPriceDiscount())
                .discount(updatedProduct.getDiscount() != null ? updatedProduct.getDiscount() : existingProduct.getDiscount())
                .stock(updatedProduct.getStock() != null ? updatedProduct.getStock() : existingProduct.getStock())
                .weight(updatedProduct.getWeight() != null ? updatedProduct.getWeight() : existingProduct.getWeight())
                .tagList(updatedTags)
                .categoryList(updatedCategories)
                .build();

        return productRepository.save(existingProductUpdated);
    }

    public Product addOrRemoveProductToWishlist(UUID productId, UUID wishlistId) {
        Product product = findById(productId);
        WishList wishlist = wishlistService.findById(wishlistId);

        if (!product.getWishlists().contains(wishlist)) {
            product.getWishlists().add(wishlist);
        } else {
            product.getWishlists().remove(wishlist);
        }

        return save(product);
    }

    public Product addProductToCart(UUID productId, UUID cartId) {
        Product product = findById(productId);
        Cart cart = cartService.findById(cartId);

        if (!product.getCartList().contains(cart)) {
            product.getCartList().add(cart);
            return save(product);
        }

        return product;
    }

    public Product removeProductToCart(UUID productId, UUID cartId) {
        Product product = findById(productId);
        Cart cart = cartService.findById(cartId);

        if (product.getCartList().contains(cart)) {
            product.getCartList().remove(cart);
            return save(product);
        }

        return save(product);
    }

    public String deleteById(UUID productId) {
        findById(productId);
        productRepository.deleteById(productId);

        return ConfirmMessage.PRODUCT_DELETE;
    }
}
