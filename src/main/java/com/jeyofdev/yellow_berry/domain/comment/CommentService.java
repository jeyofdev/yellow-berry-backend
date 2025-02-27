package com.jeyofdev.yellow_berry.domain.comment;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.core.constant.ErrorMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import com.jeyofdev.yellow_berry.domain.product.ProductService;
import com.jeyofdev.yellow_berry.domain.profile.Profile;
import com.jeyofdev.yellow_berry.domain.profile.ProfileService;
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
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment existingComment = findById(commentId);

        if (username.equals(existingComment.getProfile().getUser().getUsername())) {

            existingComment.setRating(updatedComment.getRating() != null ? updatedComment.getRating() : existingComment.getRating());
            existingComment.setBody(updatedComment.getBody() != null ? updatedComment.getBody() : existingComment.getBody());

            return commentRepository.save(existingComment);
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }

    public String deleteById(UUID commentId) {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        Comment comment = findById(commentId);

        if (username.equals(comment.getProfile().getUser().getUsername()) || roles.equals("[ROLE_ADMIN]")) {
            List<Product> productList = productRepository.findByComment(comment);

            for (Product product : productList) {
                product.getCommentList().remove(comment);
                productRepository.save(product);
            }

            comment.setProfile(null);

            commentRepository.deleteById(commentId);

            return ConfirmMessage.COMMENT_DELETE;
        } else {
            throw new AccessDeniedException(ErrorMessage.LIMIT_ACCESS);
        }
    }
}
