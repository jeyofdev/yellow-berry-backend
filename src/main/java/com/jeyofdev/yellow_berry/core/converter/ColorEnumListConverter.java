package com.jeyofdev.yellow_berry.core.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeyofdev.yellow_berry.core.enums.ColorEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Converter(autoApply = true)
public class ColorEnumListConverter implements AttributeConverter<List<ColorEnum>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ColorEnum> colors) {
        try {
            return objectMapper.writeValueAsString(colors);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting list of colors to JSON", e);
        }
    }

    @Override
    public List<ColorEnum> convertToEntityAttribute(String json) {
        try {
            return Arrays.asList(objectMapper.readValue(json, ColorEnum[].class));
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON to list of colors", e);
        }
    }
}
