package com.pragma.emazon_stock.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Brand {

    private Integer brandId;
    private String brandName;
    private String brandDescription;

}
