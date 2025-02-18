package com.jeyofdev.yellow_berry.domain.faq;

import com.jeyofdev.yellow_berry.domain.faq.dto.FaqDTO;
import com.jeyofdev.yellow_berry.domain.faq.dto.SaveFaqDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FaqMapper {
    FaqDTO mapFromEntity(Faq faq);
    Faq mapToEntity(SaveFaqDTO saveFaqDTO);
}
