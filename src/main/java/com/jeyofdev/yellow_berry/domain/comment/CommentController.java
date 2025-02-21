package com.jeyofdev.yellow_berry.domain.comment;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.comment.dto.CommentDTO;
import com.jeyofdev.yellow_berry.domain.comment.dto.SaveCommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<CommentDTO>>> findAllComments() {
        List<Comment> commentList = commentService.findAll();
        List<CommentDTO> faqDTOList = commentList.stream().map(commentMapper::mapFromEntity).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, faqDTOList);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<DomainSuccessResponse<CommentDTO>> findCommentById(@PathVariable("commentId") UUID commentId) {
        Comment comment = commentService.findById(commentId);
        CommentDTO faqDTO = commentMapper.mapFromEntity(comment);

        return DomainSuccessResponse.get(HttpStatus.OK, faqDTO);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<DomainSuccessResponse<CommentDTO>> saveComment(
            @PathVariable("productId") UUID productId,
            @RequestBody SaveCommentDTO saveCommentDTO
    ) {
        Comment comment = commentMapper.mapToEntity(saveCommentDTO);
        Comment newComment = commentService.save(productId, comment);
        CommentDTO newCommentDTO = commentMapper.mapFromEntity(newComment);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newCommentDTO);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<DomainSuccessResponse<CommentDTO>> updateCommentById(
            @PathVariable("commentId") UUID commentId,
            @RequestBody SaveCommentDTO saveCommentDTO
    ) {
        Comment comment = commentMapper.mapToEntity(saveCommentDTO);
        Comment updateComment = commentService.updateById(commentId, comment);
        CommentDTO updateCommentDTO = commentMapper.mapFromEntity(updateComment);

        return DomainSuccessResponse.get(HttpStatus.OK, updateCommentDTO);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteCommentById(@PathVariable("commentId") UUID commentId) {
        String message = commentService.deleteById(commentId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);
    }
}
