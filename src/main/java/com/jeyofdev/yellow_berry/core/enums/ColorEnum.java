package com.jeyofdev.yellow_berry.core.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jeyofdev.yellow_berry.core.serializer.ColorDeserializer;

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
    GOLD,
}
