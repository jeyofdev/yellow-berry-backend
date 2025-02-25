package com.jeyofdev.yellow_berry.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jeyofdev.yellow_berry.core.enums.ColorEnum;

import java.io.IOException;

public class ColorDeserializer extends JsonDeserializer<ColorEnum> {
    @Override
    public ColorEnum deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String value = p.getText().toUpperCase();
        return ColorEnum.valueOf(value);
    }
}
