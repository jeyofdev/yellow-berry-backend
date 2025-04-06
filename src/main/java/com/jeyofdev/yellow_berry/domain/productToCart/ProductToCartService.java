package com.jeyofdev.yellow_berry.domain.productToCart;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.cart.Cart;
import com.jeyofdev.yellow_berry.domain.cart.CartService;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.UUID;

@Service
public class ProductToCartService extends AbstractDomainService<ProductToCart, ProductToCartRepository> {
    private final CartService cartService;
    private final ProductService productService;
    private final ProductToCartRepository productToCartRepository;

    @Autowired
    public ProductToCartService(
            CartService cartService,
            ProductService productService,
            ProductToCartRepository productToCartRepository

    ) {
        super(productToCartRepository, "ProductToCart");
        this.cartService = cartService;
        this.productService = productService;
        this.productToCartRepository = productToCartRepository;
    }

    public ProductToCart save(UUID productId, UUID cartId, ProductToCart productToCart) {
        Product product = productService.findById(productId);
        Cart cart = cartService.findById(cartId);

        productToCart.setProduct(product);
        productToCart.setCart(cart);

        if (productToCartRepository.existsByCartIdAndProductId(cartId, productId)) {
            throw new IllegalArgumentException("Product to Cart with the given cart ID and product ID already exists.");
        }

        try {
            return productToCartRepository.save(productToCart);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Product to Cart with the given cart ID and product ID already exists.");
        }
    }

    public ProductToCart updateById(UUID productToCartId, ProductToCart updatedProductToCart) {
        ProductToCart existingProductToCart = findById(productToCartId);

        existingProductToCart.setQuantity(updatedProductToCart.getQuantity() != null ? updatedProductToCart.getQuantity() : existingProductToCart.getQuantity());
        existingProductToCart.setWeight(updatedProductToCart.getWeight() != null ? updatedProductToCart.getWeight() : existingProductToCart.getWeight());

        return productToCartRepository.save(existingProductToCart);
    }

    public String deleteById(UUID productToCartId) {
        ProductToCart productToCart = findById(productToCartId);

        if (productToCart.getProduct() != null) {
            productToCart.setProduct(null);
        }

        if (productToCart.getCart() != null) {
            productToCart.setCart(null);
        }

        productToCartRepository.deleteById(productToCartId);

        return MessageFormat.format(ConfirmMessage.CONFIRM_DELETE, "product to cart");
    }
}
