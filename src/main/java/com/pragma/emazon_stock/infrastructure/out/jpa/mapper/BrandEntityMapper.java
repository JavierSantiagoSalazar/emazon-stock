package com.pragma.emazon_stock.infrastructure.out.jpa.mapper;

import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandEntityMapper {

    BrandEntity toEntity(Brand brand);

    Brand toDomain(BrandEntity brandEntity);

}