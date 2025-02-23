package com.jeyofdev.yellow_berry.core.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ListResponseFormat<T> {
    private int size;
    private List<T> results;

    public ListResponseFormat(List<T> results) {
        this.results = results;
        this.size = (results != null) ? results.size() : 0;
    }
}
