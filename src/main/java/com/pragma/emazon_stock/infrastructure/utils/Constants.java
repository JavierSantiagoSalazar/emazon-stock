package com.pragma.emazon_stock.infrastructure.utils;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String DESC_COMPARATOR = "desc";

    /* --- EXCEPTIONS CONSTANTS: CATEGORY --- */

    public static final String CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The category already exists";
    public static final String CATEGORY_NO_CONTENT_MESSAGE = "There are currently no categories";

    /* --- EXCEPTIONS CONSTANTS: BRAND --- */

    public static final String BRAND_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The brand already exists";
    public static final String BRAND_NO_CONTENT_MESSAGE = "There are currently no brands";

    /* --- VALIDATION CONSTANTS: CATEGORY--- */

    public static final String CATEGORY_NAME_MUST_NOT_BE_BLANK = "The category name must not be blank";
    public static final String CATEGORY_NAME_MUST_NOT_BE_NULL = "The category name must not be null";
    public static final String CATEGORY_NAME_LENGTH_EXCEEDED = "The length of the name cannot exceed 50 characters";
    public static final String CATEGORY_DESCRIPTION_MUST_NOT_BE_BLANK = "The category description must not be blank";
    public static final String CATEGORY_DESCRIPTION_MUST_NOT_BE_NULL = "The category description must not be null";
    public static final String CATEGORY_DESCRIPTION_LENGTH_EXCEEDED = "The length of the description cannot exceed 90 characters";

    /* --- VALIDATION CONSTANTS: BRAND--- */

    public static final String BRAND_NAME_MUST_NOT_BE_BLANK = "The brand name must not be blank";
    public static final String BRAND_NAME_MUST_NOT_BE_NULL = "The brand name must not be null";
    public static final String BRAND_NAME_LENGTH_EXCEEDED = "The length of the name cannot exceed 50 characters";
    public static final String BRAND_DESCRIPTION_MUST_NOT_BE_BLANK = "The brand description must not be blank";
    public static final String BRAND_DESCRIPTION_MUST_NOT_BE_NULL = "The brand description must not be null";
    public static final String BRAND_DESCRIPTION_LENGTH_EXCEEDED = "The length of the description cannot exceed 120 characters";

    /* --- OPENAPI CONSTANTS --- */

    public static final String OPEN_API_TITLE = "Emazon Stock API";
    public static final String OPEN_API_TERMS = "http://swagger.io/terms/";
    public static final String OPEN_API_LICENCE_NAME = "Apache 2.0";
    public static final String OPEN_API_LICENCE_URL = "http://springdoc.org";
    public static final String OPEN_API_APP_DESCRIPTION = "${appDescription}";
    public static final String OPEN_API_APP_VERSION = "${appVersion}";
}
