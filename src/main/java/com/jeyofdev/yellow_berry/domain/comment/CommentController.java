package com.jeyofdev.yellow_berry.domain.comment;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.comment.dto.CommentDTO;
import com.jeyofdev.yellow_berry.domain.comment.dto.CommentPreviewDTO;
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
    public ResponseEntity<DomainSuccessResponse<List<CommentPreviewDTO>>> findAllComments() {
        List<Comment> commentList = commentService.findAll();
        List<CommentPreviewDTO> commentPreviewDTOList = commentList.stream().map(commentMapper::mapFromEntityPreview).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, commentPreviewDTOList);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<DomainSuccessResponse<CommentDTO>> findCommentById(@PathVariable("commentId") UUID commentId) {
        Comment comment = commentService.findById(commentId);
        CommentDTO commentDTO = commentMapper.mapFromEntity(comment);

        return DomainSuccessResponse.get(HttpStatus.OK, commentDTO);
    }

    @PostMapping("/product/{productId}/profile/{profileId}")
    public ResponseEntity<DomainSuccessResponse<CommentDTO>> saveComment(
            @PathVariable("productId") UUID productId,
            @PathVariable("profileId") UUID profileId,
            @RequestBody SaveCommentDTO saveCommentDTO
    ) {
        Comment comment = commentMapper.mapToEntity(saveCommentDTO);
        Comment newComment = commentService.save(productId, profileId, comment);
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
