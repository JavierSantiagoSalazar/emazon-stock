package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.api.BrandServicePort;
import com.pragma.emazon_stock.domain.exceptions.BrandAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.NoContentBrandException;
import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.model.Pagination;
import com.pragma.emazon_stock.domain.spi.BrandPersistencePort;
import com.pragma.emazon_stock.domain.utils.PaginationUtils;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;

import static com.pragma.emazon_stock.domain.utils.Constants.DESC_COMPARATOR;

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

    @Override
    public Pagination<Brand> getBrands(String sortOrder, Integer page, Integer size) {

        List<Brand> brandList = brandPersistencePort.getAllBrands();

        if (brandList.isEmpty()) {
            throw new NoContentBrandException();
        }

        brandList.sort(DESC_COMPARATOR.equalsIgnoreCase(sortOrder) ?
                Comparator.comparing(Brand::getBrandName).reversed() :
                Comparator.comparing(Brand::getBrandName));

        return PaginationUtils.paginate(brandList, page, size);
    }
    
}
