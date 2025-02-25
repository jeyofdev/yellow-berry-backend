package com.jeyofdev.yellow_berry.core.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.jeyofdev.yellow_berry.core.enums.StockEnum;

import java.io.IOException;

public class StockDeserializer extends JsonDeserializer<StockEnum> {
    @Override
    public StockEnum deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        String value = p.getText().toUpperCase();
        return StockEnum.valueOf(value);
    }
}
