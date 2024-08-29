package com.pragma.emazon_stock.domain.spi;

import com.pragma.emazon_stock.domain.model.Brand;

import java.util.List;

public interface BrandPersistencePort {

    void saveBrand(Brand brand);

    Boolean checkIfBrandExists(String brandName);

    List<Brand> getAllBrands();

    Brand getBrandByName(String brandName);

}
