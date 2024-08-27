package com.pragma.emazon_stock.infrastructure.configuration.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaginatedResponse {

    private Response response;
    private Integer pageNo;
    private Integer totalPages;

}
