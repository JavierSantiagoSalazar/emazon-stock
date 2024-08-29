package com.pragma.emazon_stock.domain.exceptions;

import com.pragma.emazon_stock.domain.utils.Constants;

import java.util.List;

public class CategoryDoesNotExistException extends RuntimeException {

    public CategoryDoesNotExistException(List<String> categoryNames) {
        super(Constants.CATEGORY_DOES_NOT_EXIST + Constants.REQUESTED_CATEGORIES + categoryNames.toString());
    }
}
