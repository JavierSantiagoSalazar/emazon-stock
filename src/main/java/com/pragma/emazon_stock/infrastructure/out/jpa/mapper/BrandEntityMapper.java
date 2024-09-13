package com.pragma.emazon_stock.infrastructure.out.jpa.mapper;

import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.utils.Constants;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = Constants.SPRING_COMPONENT_MODEL)
public interface BrandEntityMapper {

    BrandEntity toEntity(Brand brand);

    @Mapping(target = "brandArticles", ignore = true)
    Brand toDomain(BrandEntity brandEntity);

    @Mapping(target = "brandArticles", ignore = true)
    List<Brand> toBrandList(List<BrandEntity> brandEntityList);

}
