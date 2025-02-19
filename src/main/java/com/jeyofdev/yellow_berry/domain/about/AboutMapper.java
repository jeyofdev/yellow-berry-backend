package com.jeyofdev.yellow_berry.domain.about;

import com.jeyofdev.yellow_berry.domain.about.dto.SaveAboutDTO;
import com.jeyofdev.yellow_berry.domain.about.dto.AboutDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AboutMapper {
    AboutDTO mapFromEntity(About about);
    About mapToEntity(SaveAboutDTO saveAboutDTO);
}
