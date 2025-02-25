package com.jeyofdev.yellow_berry.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jeyofdev.yellow_berry.core.enums.WeightEnum;

import java.io.IOException;

public class WeightDeserializer extends JsonDeserializer<WeightEnum> {
    @Override
    public WeightEnum deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String value = p.getText().toUpperCase();
        return WeightEnum.valueOf(value);
    }
}
