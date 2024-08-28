package com.pragma.emazon_stock.application.mappers.brand;

import com.pragma.emazon_stock.application.dto.brand.BrandRequest;
import com.pragma.emazon_stock.domain.model.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface BrandRequestMapper {

    Brand toDomain(BrandRequest brandRequest);

}
