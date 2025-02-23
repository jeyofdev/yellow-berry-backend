package com.jeyofdev.yellow_berry.core.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PriceFormat {
    private Double price;
    private Double priceDiscount;
    private Double discount;
}