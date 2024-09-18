package com.pragma.emazon_stock.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Supply {

    private List<Integer> articleIds;
    private List<Integer> amounts;

}
