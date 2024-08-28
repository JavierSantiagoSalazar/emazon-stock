package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.api.BrandServicePort;
import com.pragma.emazon_stock.domain.exceptions.BrandAlreadyExistsException;
import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.spi.BrandPersistencePort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BrandUseCase implements BrandServicePort {

    private final BrandPersistencePort brandPersistencePort;

    @Override
    public void saveBrand(Brand brand) {
        if (checkIfBrandExists(brand.getBrandName())) {
            throw new BrandAlreadyExistsException();
        }
        brandPersistencePort.saveBrand(brand);
    }

    @Override
    public boolean checkIfBrandExists(String brandName) {
        return brandPersistencePort.checkIfBrandExists(brandName);
    }
    
}
