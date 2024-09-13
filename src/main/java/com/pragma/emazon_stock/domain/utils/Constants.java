package com.pragma.emazon_stock.domain.utils;

public class Constants {

    private Constants() {
        throw new IllegalStateException(UTILITY_CLASS);
    }

    public static final String SPRING_COMPONENT_MODEL = "spring";
    public static final String DESC_COMPARATOR = "desc";
    public static final String UTILITY_CLASS = "Utility class";

    /* --- USE CASES CONSTANTS --- */

    public static final Short PAGE_INDEX_HELPER = 1;
    public static final String GET_BY_ARTICLE = "articleName";
    public static final String GET_BY_BRAND = "brandName";
    public static final String GET_BY_CATEGORY = "categoryName";

    /* --- JWT CONSTANTS --- */
    public static final String CLAIM_AUTHORITIES = "authorities";
    public static final String INVALID_TOKEN = "Token invalid, not Authorized";

    /* --- SECURITY CONSTANTS --- */

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ARTICLE_URL = "/article/";
    public static final String BRAND_URL = "/brand/";
    public static final String CATEGORY_URL = "/category/";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access: ";

    /* --- EXCEPTIONS CONSTANTS --- */

    public static final String PAGE_OUT_OF_BOUNDS_PAGE = "Page ";
    public static final String PAGE_OUT_OF_BOUNDS_TOTAL_PAGES = " is out of range. Total pages: ";

    public static final String BRAND_DOES_NOT_EXIST = "The requested brand does not exist";
    public static final String REQUESTED_BRAND = ", requested brand: ";

    public static final String CATEGORY_DOES_NOT_EXIST = "The requested category does not exist";
    public static final String REQUESTED_CATEGORIES = ", requested categories: ";

    public static final String ARTICLE_INVALID_FILTER_PARAMETER = "Invalid value for filterBy parameter: ";
    public static final String ARTICLE_ALLOWED_FILTER_VALUES = ". Allowed values are 'articleName', 'brandName', 'categoryName'.";

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
    public static final String ARTICLE_NO_CONTENT_MESSAGE = "There are currently no articles";

    /* --- VALIDATION CONSTANTS: CATEGORY--- */

    public static final String CATEGORY_NAME_MUST_NOT_BE_BLANK = "The category name must not be blank";
    public static final String CATEGORY_NAME_MUST_NOT_BE_NULL = "The category name must not be null";
    public static final String CATEGORY_NAME_LENGTH_EXCEEDED = "The length of the name cannot exceed 50 characters";
    public static final int CATEGORY_NAME_LENGTH = 50;
    public static final String CATEGORY_DESCRIPTION_MUST_NOT_BE_BLANK = "The category description must not be blank";
    public static final String CATEGORY_DESCRIPTION_MUST_NOT_BE_NULL = "The category description must not be null";
    public static final String CATEGORY_DESCRIPTION_LENGTH_EXCEEDED = "The length of the description cannot exceed 90 characters";
    public static final int CATEGORY_DESCRIPTION_LENGTH = 90;

    /* --- VALIDATION CONSTANTS: BRAND--- */

    public static final String BRAND_NAME_MUST_NOT_BE_BLANK = "The brand name must not be blank";
    public static final String BRAND_NAME_MUST_NOT_BE_NULL = "The brand name must not be null";
    public static final String BRAND_NAME_LENGTH_EXCEEDED = "The length of the name cannot exceed 50 characters";
    public static final int BRAND_NAME_LENGTH = 50;
    public static final String BRAND_DESCRIPTION_MUST_NOT_BE_BLANK = "The brand description must not be blank";
    public static final String BRAND_DESCRIPTION_MUST_NOT_BE_NULL = "The brand description must not be null";
    public static final String BRAND_DESCRIPTION_LENGTH_EXCEEDED = "The length of the description cannot exceed 120 characters";
    public static final int BRAND_DESCRIPTION_LENGTH = 120;

    /* --- VALIDATION CONSTANTS: ARTICLE --- */

    public static final String ARTICLE_NAME_MUST_NOT_BE_BLANK = "The article name must not be blank";
    public static final String ARTICLE_NAME_MUST_NOT_BE_NULL = "The article name must not be null";
    public static final String ARTICLE_NAME_LENGTH_EXCEEDED = "The length of the name cannot exceed 120 characters";
    public static final int ARTICLE_NAME_LENGTH = 120;
    public static final String ARTICLE_DESCRIPTION_MUST_NOT_BE_BLANK = "The article description must not be blank";
    public static final String ARTICLE_DESCRIPTION_MUST_NOT_BE_NULL = "The article description must not be null";
    public static final String ARTICLE_DESCRIPTION_LENGTH_EXCEEDED = "The length of the description cannot exceed 160 characters";
    public static final int ARTICLE_DESCRIPTION_LENGTH = 160;
    public static final String ARTICLE_AMOUNT_MUST_NOT_BE_NULL = "The article amount must not be null";
    public static final String ARTICLE_AMOUNT_CANNOT_BE_NEGATIVE = "The article amount can not be negative";
    public static final String ARTICLE_PRICE_MUST_NOT_BE_NULL = "The article price must not be null";
    public static final String ARTICLE_PRICE_CANNOT_BE_NEGATIVE = "The article price can not be negative";
    public static final String ARTICLE_PRICE_MINIMAL_VALUE = "The article price can not be lower than 0.01";
    public static final String ARTICLE_PRICE_LENGTH = "0.01";
    public static final String ARTICLE_BRAND_MUST_NOT_BE_NULL = "The article brand must not be null";
    public static final String ARTICLE_CATEGORIES_MUST_NOT_BE_NULL = "The article categories must not be null";

    /* --- OPENAPI CONSTANTS --- */

    public static final String OPEN_API_TITLE = "Emazon Stock API";
    public static final String OPEN_API_TERMS = "http://swagger.io/terms/";
    public static final String OPEN_API_LICENCE_NAME = "Apache 2.0";
    public static final String OPEN_API_LICENCE_URL = "http://springdoc.org";
    public static final String OPEN_API_APP_DESCRIPTION = "${appDescription}";
    public static final String OPEN_API_APP_VERSION = "${appVersion}";
    public static final String OPEN_API_SWAGGER_UI_HTML = "/swagger-ui/**";
    public static final String OPEN_API_SWAGGER_UI = "/swagger-ui/";
    public static final String OPEN_API_V3_API_DOCS = "/v3/api-docs/**";

    public static final String ARTICLE_SUMMARY = "Add new article";
    public static final String ARTICLE_GET_ALL_SUMMARY = "Get all articles";

    public static final String ARTICLE_CREATED = "Article created";
    public static final String ARTICLE_ALREADY_EXISTS = "Article already exists";
    public static final String ARTICLE_DUPLICATE_CATEGORY_NAMES = "Article has duplicated names in the article categories";
    public static final String ARTICLE_CATEGORY_LIMIT = "Article categories must be between 1 and 3";
    public static final String ARTICLE_OBTAINED = "Articles obtained";
    public static final String ARTICLE_NO_CONTENT = "No content for article";
    public static final String ARTICLE_INVALID_FILTER = "Invalid parameter filterBy";
    public static final String ARTICLE_PAGE_OUT_OF_RANGE = "The page requested is out of range";

    public static final String ADD_NEW_BRAND_SUMMARY = "Add new brand";
    public static final String GET_ALL_BRANDS_SUMMARY = "Get all brands";

    public static final String BRAND_CREATED = "Brand created";
    public static final String BRAND_ALREADY_EXISTS = "Brand already exists";
    public static final String BRANDS_OBTAINED = "Brands obtained";
    public static final String PAGE_OUT_OF_RANGE = "The page requested is out of range";

    public static final String ADD_NEW_CATEGORY_SUMMARY = "Add new category";
    public static final String GET_ALL_CATEGORIES_SUMMARY = "Get all categories";

    public static final String CATEGORY_CREATED = "Category created";
    public static final String CATEGORY_ALREADY_EXISTS = "Category already exists";
    public static final String CATEGORIES_OBTAINED = "Categories obtained";

    /* --- REQUEST CONSTANTS --- */

    public static final String PAGE_DEFAULT_VALUE = "1";
    public static final String SIZE_DEFAULT_VALUE = "10";

    public static final String ASC_SORT_ORDER = "asc";
    public static final String ARTICLE_NAME = "articleName";

}
