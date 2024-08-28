package com.pragma.emazon_stock.application.handler.brand;

import com.pragma.emazon_stock.application.dto.brand.BrandRequest;
import com.pragma.emazon_stock.application.dto.brand.BrandResponse;
import com.pragma.emazon_stock.application.mappers.brand.BrandRequestMapper;
import com.pragma.emazon_stock.application.mappers.brand.BrandResponseMapper;
import com.pragma.emazon_stock.domain.api.BrandServicePort;
import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class BrandHandlerImpl implements BrandHandler {

    private final BrandServicePort brandServicePort;
    private final BrandRequestMapper brandRequestMapper;
    private final BrandResponseMapper brandResponseMapper;

    @Override
    public void createBrand(BrandRequest brandRequest) {

        brandRequest.setBrandName(brandRequest.getBrandName().trim().toUpperCase());
        brandRequest.setBrandDescription(brandRequest.getBrandDescription().trim());

        brandServicePort.saveBrand(brandRequestMapper.toDomain(brandRequest));

    }

    @Override
    public Pagination<BrandResponse> getBrands(String sortOrder, Integer page, Integer size) {

        Pagination<Brand> paginationBrands = brandServicePort.getBrands(sortOrder, page, size);

        List<BrandResponse> brandResponses = paginationBrands.getItems().stream()
                .map(brandResponseMapper::toResponse)
                .toList();

        return new Pagination<>(
                brandResponses,
                paginationBrands.getPageNo(),
                paginationBrands.getPageSize(),
                paginationBrands.getTotalItems(),
                paginationBrands.getTotalPages(),
                paginationBrands.getIsLastPage()
        );

    }

}
