package com.jeyofdev.yellow_berry.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
    private String exceptionName;
    private String date;
    private String path;
}