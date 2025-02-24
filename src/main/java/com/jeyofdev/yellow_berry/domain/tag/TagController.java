package com.jeyofdev.yellow_berry.domain.tag;

import com.jeyofdev.yellow_berry.core.model.DomainSuccessResponse;
import com.jeyofdev.yellow_berry.domain.tag.dto.TagDTO;
import com.jeyofdev.yellow_berry.domain.tag.dto.SaveTagDTO;
import com.jeyofdev.yellow_berry.domain.tag.dto.TagPreviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagMapper tagMapper;

    @GetMapping
    public ResponseEntity<DomainSuccessResponse<List<TagDTO>>> findAllTags() {
        List<Tag> tagList = tagService.findAll();
        List<TagDTO> tagDTOList = tagList.stream().map(tagMapper::mapFromEntity).toList();

        return DomainSuccessResponse.get(HttpStatus.OK, tagDTOList);
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<DomainSuccessResponse<TagDTO>> findTagById(@PathVariable("tagId") UUID tagId) {
        Tag tag = tagService.findById(tagId);
        TagDTO tagDTO = tagMapper.mapFromEntity(tag);

        return DomainSuccessResponse.get(HttpStatus.OK, tagDTO);
    }

    @PostMapping
    public ResponseEntity<DomainSuccessResponse<TagPreviewDTO>> saveTag(@RequestBody SaveTagDTO saveTagDTO) {
        Tag tag = tagMapper.mapToEntity(saveTagDTO);
        Tag newTag = tagService.save(tag);
        TagPreviewDTO newTagPreviewDTO = tagMapper.mapFromEntityPreview(newTag);

        return DomainSuccessResponse.get(HttpStatus.CREATED, newTagPreviewDTO);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<DomainSuccessResponse<TagDTO>> updateTagById(
            @PathVariable("tagId") UUID tagId,
            @RequestBody SaveTagDTO saveTagDTO
    ) {
        Tag tag = tagMapper.mapToEntity(saveTagDTO);
        Tag updateTag = tagService.updateById(tagId, tag);
        TagDTO updateTagDTO = tagMapper.mapFromEntity(updateTag);

        return DomainSuccessResponse.get(HttpStatus.OK, updateTagDTO);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<DomainSuccessResponse<Object>> deleteTagById(@PathVariable("tagId") UUID tagId) {
        String message = tagService.deleteById(tagId);

        return DomainSuccessResponse.get(HttpStatus.OK, message);

    }
}
