package com.jeyofdev.yellow_berry.core.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeyofdev.yellow_berry.core.serializer.StockDeserializer;

@JsonDeserialize(using = StockDeserializer.class)
public enum StockEnum {
    IN_STOCK,
    OUT_OF_STOCK

}
