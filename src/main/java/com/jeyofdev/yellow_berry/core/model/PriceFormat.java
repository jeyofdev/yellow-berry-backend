package com.jeyofdev.yellow_berry.core.model;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PriceFormat {
    private Double price;
    private Double priceDiscount;
    private Integer discount;

    public void setPriceDiscount() {
        if (price != null && discount != null && price > 0 && discount >= 0 && discount <= 100) {
            System.out.println("1");
            BigDecimal discountedPrice = BigDecimal.valueOf(price * (1 - discount / 100.0))
                    .setScale(2, RoundingMode.HALF_UP);
            this.priceDiscount = discountedPrice.doubleValue();
        } else {
            System.out.println("2");
            this.priceDiscount = price != null ? BigDecimal.valueOf(price)
                    .setScale(2, RoundingMode.HALF_UP).doubleValue() : null;
        }
    }
}