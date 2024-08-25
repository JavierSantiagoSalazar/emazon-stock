package com.pragma.emazon_stock.infrastructure.utils;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    /* --- EXCEPTIONS CONSTANTS --- */
    public static final String CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The category already exists";

    /* --- VALIDATIONS CONSTANTS --- */

    public static final String CATEGORY_NAME_MUST_NOT_BE_BLANK = "The category name must not be blank";
    public static final String CATEGORY_NAME_MUST_NOT_BE_NULL = "The category name must not be null";
    public static final String CATEGORY_NAME_LENGTH_EXCEEDED = "The length of the name cannot exceed 50 characters";
    public static final String CATEGORY_DESCRIPTION_MUST_NOT_BE_BLANK = "The category description must not be blank";
    public static final String CATEGORY_DESCRIPTION_MUST_NOT_BE_NULL = "The category description must not be null";
    public static final String CATEGORY_DESCRIPTION_LENGTH_EXCEEDED = "The length of the description cannot exceed 90 characters";

}
