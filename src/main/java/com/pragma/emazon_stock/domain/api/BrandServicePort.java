package com.pragma.emazon_stock.domain.api;

import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.model.Pagination;

public interface BrandServicePort {

    void saveBrand(Brand brand);

    boolean checkIfBrandExists(String brandName);

    Pagination<Brand> getBrands(String sortOrder, Integer page, Integer size);

}
