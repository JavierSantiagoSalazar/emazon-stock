package com.pragma.emazon_stock.domain.exceptions;

import com.pragma.emazon_stock.domain.utils.Constants;

public class SupplyAmountMismatchException extends RuntimeException {

    public SupplyAmountMismatchException() {
        super(Constants.SUPPLY_MISMATCH);
    }
}
