package com.jeyofdev.yellow_berry.domain.tag;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TagService extends AbstractDomainService<Tag, TagRepository> {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        super(tagRepository, "Tag");
        this.tagRepository = tagRepository;
    }

    public Tag updateById(UUID tagId, Tag updatedTag) {
        Tag existingTag = findById(tagId);
        Tag existingTagUpdated = Tag.builder()
                .id(tagId)
                .name(updatedTag.getName() != null ? updatedTag.getName() : existingTag.getName())
                .build();

        return tagRepository.save(existingTagUpdated);
    }

    public String deleteById(UUID tagId) {
        findById(tagId);
        tagRepository.deleteById(tagId);

        return ConfirmMessage.TAG_DELETE;
    }
}
