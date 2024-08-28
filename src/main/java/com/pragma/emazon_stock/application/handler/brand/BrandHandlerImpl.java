package com.pragma.emazon_stock.application.handler.brand;

import com.pragma.emazon_stock.application.dto.BrandRequest;
import com.pragma.emazon_stock.application.mappers.brand.BrandRequestMapper;
import com.pragma.emazon_stock.domain.api.BrandServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class BrandHandlerImpl implements BrandHandler {

    private final BrandServicePort brandServicePort;
    private final BrandRequestMapper brandRequestMapper;

    @Override
    public void createBrand(BrandRequest brandRequest) {

        brandRequest.setBrandName(brandRequest.getBrandName().trim().toUpperCase());
        brandRequest.setBrandDescription(brandRequest.getBrandDescription().trim());

        brandServicePort.saveBrand(brandRequestMapper.toDomain(brandRequest));

    }

}
