package com.jeyofdev.yellow_berry.core.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddressFormat {
    private String address;
    private String zipCode;
    private String city;
    private String department;
    private String region;
}
