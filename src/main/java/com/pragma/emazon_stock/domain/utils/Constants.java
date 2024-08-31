package com.pragma.emazon_stock.domain.utils;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }

    /* --- EXCEPTIONS CONSTANTS --- */

    public static final String PAGE_OUT_OF_BOUNDS_PAGE = "Page ";
    public static final String PAGE_OUT_OF_BOUNDS_TOTAL_PAGES = " is out of range. Total pages: ";

    public static final String BRAND_DOES_NOT_EXIST = "The requested brand does not exist";
    public static final String REQUESTED_BRAND = ", requested brand: ";

    public static final String CATEGORY_DOES_NOT_EXIST = "The requested category does not exist";
    public static final String REQUESTED_CATEGORIES = ", requested categories: ";

    public static final String ARTICLE_INVALID_FILTER_PARAMETER = "Invalid value for filterBy parameter: ";
    public static final String ARTICLE_ALLOWED_FILTER_VALUES = ". Allowed values are 'articleName', 'brandName', 'categoryName'.";



}
