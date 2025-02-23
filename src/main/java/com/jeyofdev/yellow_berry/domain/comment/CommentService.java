package com.jeyofdev.yellow_berry.domain.comment;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import com.jeyofdev.yellow_berry.domain.product.ProductService;
import com.jeyofdev.yellow_berry.domain.profile.Profile;
import com.jeyofdev.yellow_berry.domain.profile.ProfileRepository;
import com.jeyofdev.yellow_berry.domain.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService extends AbstractDomainService<Comment, CommentRepository> {
    private final CommentRepository commentRepository;
    private final ProductService productService;
    private final ProfileService profileService;
    private final ProductRepository productRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public CommentService(
            CommentRepository commentRepository,
            ProductService productService,
            ProfileService profileService,
            ProductRepository productRepository,
            ProfileRepository profileRepository
    ) {
        super(commentRepository, "Comment");
        this.commentRepository = commentRepository;
        this.productService = productService;
        this.profileService = profileService;
        this.productRepository = productRepository;
        this.profileRepository = profileRepository;
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

        existingComment.setRating(updatedComment.getRating() != null ? updatedComment.getRating() : existingComment.getRating());
        existingComment.setBody(updatedComment.getBody() != null ? updatedComment.getBody() : existingComment.getBody());

        return commentRepository.save(existingComment);
    }

    public String deleteById(UUID commentId) {
        Comment comment = findById(commentId);
        List<Product> productList = productRepository.findByComment(comment);
        Profile profile = profileRepository.findByComment(comment);

        for (Product product : productList) {
            product.getCommentList().remove(comment);
            productRepository.save(product);
        }

        comment.setProfile(null);

        commentRepository.deleteById(commentId);

        return ConfirmMessage.COMMENT_DELETE;
    }
}
