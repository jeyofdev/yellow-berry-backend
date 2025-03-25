package com.jeyofdev.yellow_berry.core.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeyofdev.yellow_berry.core.serializer.StockDeserializer;
import lombok.Getter;

@Getter
@JsonDeserialize(using = StockDeserializer.class)
public enum StockEnum {
    IN_STOCK("In stock"),
    OUT_OF_STOCK("Out of stock");

    private final String label;

    StockEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
