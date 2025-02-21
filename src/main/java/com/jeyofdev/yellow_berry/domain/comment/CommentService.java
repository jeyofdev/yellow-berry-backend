package com.jeyofdev.yellow_berry.domain.comment;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentService extends AbstractDomainService<Comment, CommentRepository> {
    private final CommentRepository commentRepository;
    private final ProductService productService;

    @Autowired
    public CommentService(CommentRepository commentRepository, ProductService productService) {
        super(commentRepository, "Comment");
        this.commentRepository = commentRepository;
        this.productService = productService;
    }

    public Comment save(UUID productId, Comment comment) {
        Product product = productService.findById(productId);
        comment.setProduct(product);

        return commentRepository.save(comment);
    }

    public Comment updateById(UUID commentId, Comment updatedComment) {
        Comment existingComment = findById(commentId);
        Comment existingCommentUpdated = Comment.builder()
                .id(commentId)
                .firstname(updatedComment.getFirstname() != null ? updatedComment.getFirstname() : existingComment.getFirstname())
                .lastname(updatedComment.getLastname() != null ? updatedComment.getLastname() : existingComment.getLastname())
                .rating(updatedComment.getRating() != null ? updatedComment.getRating() : existingComment.getRating())
                .body(updatedComment.getBody() != null ? updatedComment.getBody() : existingComment.getBody())
                .product(existingComment.getProduct())
                .build();

        return commentRepository.save(existingCommentUpdated);
    }

    public String deleteById(UUID commentId) {
        findById(commentId);
        commentRepository.deleteById(commentId);

        return ConfirmMessage.COMMENT_DELETE;
    }
}
