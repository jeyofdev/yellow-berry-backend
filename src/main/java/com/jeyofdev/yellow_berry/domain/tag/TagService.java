package com.jeyofdev.yellow_berry.domain.tag;

import com.jeyofdev.yellow_berry.core.classes.AbstractDomainService;
import com.jeyofdev.yellow_berry.core.constant.ConfirmMessage;
import com.jeyofdev.yellow_berry.domain.product.Product;
import com.jeyofdev.yellow_berry.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagService extends AbstractDomainService<Tag, TagRepository> {
    private final TagRepository tagRepository;
    private final ProductRepository productRepository;

    @Autowired
    public TagService(TagRepository tagRepository, ProductRepository productRepository) {
        super(tagRepository, "Tag");
        this.tagRepository = tagRepository;
        this.productRepository = productRepository;
    }

    public List<Tag> getTagsByIds(List<UUID> tagIds) {
        return tagIds == null || tagIds.isEmpty() ? List.of() : tagRepository.findAllById(tagIds);
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
        Tag tag = findById(tagId);
        List<Product> productList = productRepository.findByTag(tag);

        for (Product product : productList) {
            product.getTagList().remove(tag);
            productRepository.save(product);
        }

        tagRepository.deleteById(tagId);

        return ConfirmMessage.TAG_DELETE;
    }
}
