package com.jeyofdev.yellow_berry.core.mappers;

import com.jeyofdev.yellow_berry.core.model.ListResponseFormat;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ListResponseFormatMapper {
    @Named("toListResponseFormat")
    default <T> ListResponseFormat<T> toListResponseFormat(List<T> list) {
        return new ListResponseFormat<>(list);
    }
}