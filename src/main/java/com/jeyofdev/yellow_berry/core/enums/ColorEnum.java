package com.jeyofdev.yellow_berry.core.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeyofdev.yellow_berry.core.serializer.ColorDeserializer;

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

    public static ColorEnum getRandomColor() {
        ColorEnum[] colors = ColorEnum.values();
        return colors[new Random().nextInt(colors.length)];
    }
}
