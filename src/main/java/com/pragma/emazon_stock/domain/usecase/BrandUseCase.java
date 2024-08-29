package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.api.BrandServicePort;
import com.pragma.emazon_stock.domain.exceptions.BrandAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.NoContentBrandException;
import com.pragma.emazon_stock.domain.exceptions.PageOutOfBoundsException;
import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.model.Pagination;
import com.pragma.emazon_stock.domain.spi.BrandPersistencePort;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;

import static com.pragma.emazon_stock.infrastructure.utils.Constants.DESC_COMPARATOR;

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

        if (DESC_COMPARATOR.equalsIgnoreCase(sortOrder)) {
            brandList.sort(Comparator.comparing(Brand::getBrandName).reversed());
        } else {
            brandList.sort(Comparator.comparing(Brand::getBrandName));
        }

        if (brandList.isEmpty()) {
            throw new NoContentBrandException();
        }

        Integer totalItems = brandList.size();
        Integer totalPages = (int) Math.ceil((double) totalItems / size);
        Integer fromIndex = Math.min((page - 1) * size, totalItems);
        Integer toIndex = Math.min(fromIndex + size, totalItems);
        Boolean isLastPage = page >= totalPages;

        if (page > totalPages || page < 1) {
            throw new PageOutOfBoundsException(page, totalPages);
        }

        List<Brand> paginatedBrands = brandList.subList(fromIndex, toIndex);

        return new Pagination<>(
                paginatedBrands,
                page,
                size,
                (long) totalItems,
                totalPages,
                isLastPage
        );
    }
    
}
