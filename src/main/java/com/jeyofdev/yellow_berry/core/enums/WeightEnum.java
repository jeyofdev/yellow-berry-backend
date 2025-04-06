package com.jeyofdev.yellow_berry.core.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeyofdev.yellow_berry.core.serializer.WeightDeserializer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Getter
@JsonDeserialize(using = WeightDeserializer.class)
public enum WeightEnum {
    GRAM_250(250),
    GRAM_500(500),
    GRAM_1000(1000),
    GRAM_2000(2000),
    GRAM_3000(3000),
    GRAM_4000(4000),
    GRAM_5000(5000),
    GRAM_6000(6000),
    GRAM_7000(7000),
    GRAM_8000(8000),
    GRAM_9000(9000),
    GRAM_10000(10000);

    private final int grams;
    private static final Random RANDOM = new Random();

    WeightEnum(int grams) {
        this.grams = grams;
    }

    @Override
    public String toString() {
        if (grams >= 1000) {
            return (grams / 1000) + "kg";
        }
        return grams + "g";
    }

    public static WeightEnum getRandomEnum() {
        WeightEnum[] weights = WeightEnum.values();
        return weights[new Random().nextInt(weights.length)];
    }

    public static List<WeightEnum> getRandomWeightList(int numberOfColors) {
        List<WeightEnum> weightList = new ArrayList<>(List.of(values()));
        Collections.shuffle(weightList, RANDOM);
        return weightList.subList(0, Math.min(numberOfColors, weightList.size()));
    }
}
