package com.pragma.emazon_stock.application.mappers.brand;

import com.pragma.emazon_stock.application.dto.brand.BrandResponse;
import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = Constants.SPRING_COMPONENT_MODEL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface BrandResponseMapper {

    BrandResponse toResponse(Brand brand);

}
