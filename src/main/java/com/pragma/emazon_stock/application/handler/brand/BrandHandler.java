package com.pragma.emazon_stock.application.handler.brand;

import com.pragma.emazon_stock.application.dto.brand.BrandRequest;
import com.pragma.emazon_stock.application.dto.brand.BrandResponse;
import com.pragma.emazon_stock.domain.model.Pagination;

public interface BrandHandler {

    void createBrand(BrandRequest brandRequest);

    Pagination<BrandResponse> getBrands(String sortOrder, Integer page, Integer size);

}
