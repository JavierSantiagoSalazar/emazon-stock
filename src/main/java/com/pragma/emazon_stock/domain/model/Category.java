package com.pragma.emazon_stock.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    private Integer id;
    private String name;
    private String description;

}
