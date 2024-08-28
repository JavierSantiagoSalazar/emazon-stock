package com.pragma.emazon_stock.domain.api;

import com.pragma.emazon_stock.domain.model.Brand;

public interface BrandServicePort {

    void saveBrand(Brand brand);

    boolean checkIfBrandExists(String brandName);

}
