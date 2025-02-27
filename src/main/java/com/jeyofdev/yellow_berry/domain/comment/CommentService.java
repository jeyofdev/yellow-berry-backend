package com.jeyofdev.yellow_berry.domain.comment;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import com.jeyofdev.yellow_berry.domain.product.ProductService;
import com.jeyofdev.yellow_berry.domain.profile.Profile;
import com.jeyofdev.yellow_berry.domain.profile.ProfileService;
import com.jeyofdev.yellow_berry.security.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService extends AbstractDomainService<Comment, CommentRepository> {
    private final CommentRepository commentRepository;
    private final ProductService productService;
    private final ProfileService profileService;
    private final ProductRepository productRepository;

    @Autowired
    public CommentService(
            CommentRepository commentRepository,
            ProductService productService,
            ProfileService profileService,
            ProductRepository productRepository
    ) {
        super(commentRepository, "Comment");
        this.commentRepository = commentRepository;
        this.productService = productService;
        this.profileService = profileService;
        this.productRepository = productRepository;
    }

    public Comment save(UUID productId, UUID profileId, Comment comment) {
        Product product = productService.findById(productId);
        Profile profile = profileService.findById(profileId);

        comment.setProduct(product);
        comment.setProfile(profile);

        return commentRepository.save(comment);
    }

    public Comment updateById(UUID commentId, Comment updatedComment) {
        Comment existingComment = findById(commentId);
        SecurityUtil.checkAuthenticatedUserOrAdminIsAuthorized(existingComment.getProfile().getUser().getUsername(), false);

        existingComment.setRating(updatedComment.getRating() != null ? updatedComment.getRating() : existingComment.getRating());
        existingComment.setBody(updatedComment.getBody() != null ? updatedComment.getBody() : existingComment.getBody());

        return commentRepository.save(existingComment);
    }

    public String deleteById(UUID commentId) {
        Comment comment = findById(commentId);
        SecurityUtil.checkAuthenticatedUserOrAdminIsAuthorized(comment.getProfile().getUser().getUsername(), true);

        List<Product> productList = productRepository.findByComment(comment);

        for (Product product : productList) {
            product.getCommentList().remove(comment);
            productRepository.save(product);
        }

        comment.setProfile(null);

        commentRepository.deleteById(commentId);

        return ConfirmMessage.COMMENT_DELETE;
    }
}
