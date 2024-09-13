package com.pragma.emazon_stock.domain.utils;

import com.pragma.emazon_stock.domain.exceptions.PageOutOfBoundsException;
import com.pragma.emazon_stock.domain.model.Pagination;

import java.util.List;

import static com.pragma.emazon_stock.domain.utils.Constants.PAGE_INDEX_HELPER;
import static com.pragma.emazon_stock.domain.utils.Constants.UTILITY_CLASS;

public class PaginationUtils {

    private PaginationUtils() {
        throw new UnsupportedOperationException(UTILITY_CLASS);
    }

    public static <T> Pagination<T> paginate(List<T> itemList, Integer page, Integer size) {
        int totalItems = itemList.size();
        int totalPages = (int) Math.ceil((double) totalItems / size);
        int fromIndex = Math.min((page - PAGE_INDEX_HELPER) * size, totalItems);
        int toIndex = Math.min(fromIndex + size, totalItems);
        boolean isLastPage = page >= totalPages;

        if (page > totalPages || page < PAGE_INDEX_HELPER) {
            throw new PageOutOfBoundsException(page, totalPages);
        }

        List<T> paginatedItems = itemList.subList(fromIndex, toIndex);

        return new Pagination<>(
                paginatedItems,
                page,
                size,
                (long) totalItems,
                totalPages,
                isLastPage
        );
    }

}
