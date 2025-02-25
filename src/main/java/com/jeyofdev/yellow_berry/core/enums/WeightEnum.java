package com.jeyofdev.yellow_berry.core.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeyofdev.yellow_berry.core.serializer.WeightDeserializer;
import lombok.Getter;

@Getter
@JsonDeserialize(using = WeightDeserializer.class)
public enum WeightEnum {
    GRAM_250(250),
    GRAM_500(500),
    GRAM_1000(1000),
    GRAM_2000(2000);

    private final int grams;

    WeightEnum (int grams) {
        this.grams = grams;
    }

    @Override
    public String toString() {
        if (grams >= 1000) {
            return (grams / 1000) + "kg";
        }
        return grams + "g";
    }

    public static void main(String[] args) {
        for (WeightEnum w : WeightEnum.values()) {
            System.out.println(w + " = " + w.toString());
        }
    }

}
