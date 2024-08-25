package com.pragma.emazon_stock.infrastructure.configuration.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class Response {

    private HttpStatus statusCode;
    private String message;

}
