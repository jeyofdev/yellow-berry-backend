package com.jeyofdev.yellow_berry.core.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NameFormat {
    private String firstname;
    private String lastname;
    private String fullname;

    public void setFullname() {
        this.fullname = this.firstname + " " + this.lastname;
    }
}
