package com.jeyofdev.yellow_berry.core.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RatingFormat {
    private Integer rating;
    private Integer count;
}
