package com.jeyofdev.yellow_berry.core.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeyofdev.yellow_berry.core.serializer.ColorDeserializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@JsonDeserialize(using = ColorDeserializer.class)
public enum ColorEnum {
    BLACK,
    WHITE,
    RED,
    BLUE,
    GREEN,
    YELLOW,
    ORANGE,
    PINK,
    PURPLE,
    BROWN,
    SILVER,
    GOLD;

    private static final Random RANDOM = new Random();

    public static ColorEnum getRandomColor() {
        ColorEnum[] colors = ColorEnum.values();
        return colors[new Random().nextInt(colors.length)];
    }

    public static List<ColorEnum> getRandomColorList(int numberOfColors) {
        List<ColorEnum> colorList = new ArrayList<>(List.of(values()));
        Collections.shuffle(colorList, RANDOM);
        return colorList.subList(0, Math.min(numberOfColors, colorList.size()));
    }
}
