package com.pragma.emazon_stock.domain.exceptions;

import com.pragma.emazon_stock.domain.utils.Constants;

public class InvalidFilteringParameterException extends RuntimeException {
    public InvalidFilteringParameterException(String filterBy) {
        super(Constants.ARTICLE_INVALID_FILTER_PARAMETER + filterBy + Constants.ARTICLE_ALLOWED_FILTER_VALUES);
    }
}