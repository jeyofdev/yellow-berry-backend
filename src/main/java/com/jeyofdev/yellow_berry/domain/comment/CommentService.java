package com.jeyofdev.yellow_berry.domain.comment;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CommentService extends AbstractDomainService<Comment, CommentRepository> {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        super(commentRepository, "Comment");
        this.commentRepository = commentRepository;
    }

    public Comment updateById(UUID commentId, Comment updatedComment) {
        Comment existingComment = findById(commentId);
        Comment existingCommentUpdated = Comment.builder()
                .id(commentId)
                .firstname(updatedComment.getFirstname() != null ? updatedComment.getFirstname() : existingComment.getFirstname())
                .lastname(updatedComment.getLastname() != null ? updatedComment.getLastname() : existingComment.getLastname())
                .rating(updatedComment.getRating() != null ? updatedComment.getRating() : existingComment.getRating())
                .body(updatedComment.getBody() != null ? updatedComment.getBody() : existingComment.getBody())
                .build();

        return commentRepository.save(existingCommentUpdated);
    }

    public String deleteById(UUID commentId) {
        findById(commentId);
        commentRepository.deleteById(commentId);

        return ConfirmMessage.COMMENT_DELETE;
    }
}
