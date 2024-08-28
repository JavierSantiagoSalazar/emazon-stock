package com.pragma.emazon_stock.domain.spi;

import com.pragma.emazon_stock.domain.model.Brand;

public interface BrandPersistencePort {

    void saveBrand(Brand brand);

    Boolean checkIfBrandExists(String brandName);

}
