package com.pragma.emazon_stock.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SupplyTransaction {

    private Integer articleId;
    private Integer articleAmount;

}
