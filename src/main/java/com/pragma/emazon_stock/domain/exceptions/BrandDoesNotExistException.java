package com.pragma.emazon_stock.domain.exceptions;

import com.pragma.emazon_stock.domain.utils.Constants;

public class BrandDoesNotExistException extends RuntimeException {

    public BrandDoesNotExistException(String brandName) {
        super(Constants.BRAND_DOES_NOT_EXIST + Constants.REQUESTED_BRAND + brandName);
    }
}
