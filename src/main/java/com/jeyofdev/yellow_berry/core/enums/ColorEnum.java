package com.jeyofdev.yellow_berry.core.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeyofdev.yellow_berry.core.serializer.ColorDeserializer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Getter
@JsonDeserialize(using = ColorDeserializer.class)
public enum ColorEnum {
    RED("Red"),
    BLUE("Blue"),
    GREEN("Green"),
    YELLOW("Yellow"),
    ORANGE("Orange"),
    PINK("Pink"),
    PURPLE("Purple"),
    BROWN("Brown"),
    SILVER("Silver"),
    GOLD("Gold");

    private static final Random RANDOM = new Random();
    private final String label;

    ColorEnum(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }

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
