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

    /* --- EXCEPTIONS CONSTANTS: ARTICLE --- */
    public static final String ARTICLE_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The article already exists";
    public static final String ARTICLE_CATEGORIES_NOT_UNIQUE_MESSAGE = "There are duplicated names in the article categories";
    public static final String ARTICLE_CATEGORIES_OUT_OF_BOUNDS_MESSAGE = "Article categories must be between 1 and 3";

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

    /* --- VALIDATION CONSTANTS: ARTICLE --- */

    public static final String ARTICLE_NAME_MUST_NOT_BE_BLANK = "The article name must not be blank";
    public static final String ARTICLE_NAME_MUST_NOT_BE_NULL = "The article name must not be null";
    public static final String ARTICLE_NAME_LENGTH_EXCEEDED = "The length of the name cannot exceed 120 characters";
    public static final String ARTICLE_DESCRIPTION_MUST_NOT_BE_BLANK = "The article description must not be blank";
    public static final String ARTICLE_DESCRIPTION_MUST_NOT_BE_NULL = "The article description must not be null";
    public static final String ARTICLE_DESCRIPTION_LENGTH_EXCEEDED = "The length of the description cannot exceed 160 characters";
    public static final String ARTICLE_AMOUNT_MUST_NOT_BE_NULL = "The article amount must not be null";
    public static final String ARTICLE_AMOUNT_CANNOT_BE_NEGATIVE = "The article amount can not be negative";
    public static final String ARTICLE_PRICE_MUST_NOT_BE_NULL = "The article price must not be null";
    public static final String ARTICLE_PRICE_CANNOT_BE_NEGATIVE = "The article price can not be negative";
    public static final String ARTICLE_PRICE_MINIMAL_VALUE = "The article price can not be lower than 0.01";
    public static final String ARTICLE_CATEGORIES_MUST_NOT_BE_NULL = "The article categories must not be null";

    /* --- OPENAPI CONSTANTS --- */

    public static final String OPEN_API_TITLE = "Emazon Stock API";
    public static final String OPEN_API_TERMS = "http://swagger.io/terms/";
    public static final String OPEN_API_LICENCE_NAME = "Apache 2.0";
    public static final String OPEN_API_LICENCE_URL = "http://springdoc.org";
    public static final String OPEN_API_APP_DESCRIPTION = "${appDescription}";
    public static final String OPEN_API_APP_VERSION = "${appVersion}";

}
