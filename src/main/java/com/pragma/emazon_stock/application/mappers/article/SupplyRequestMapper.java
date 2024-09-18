package com.pragma.emazon_stock.application.mappers.article;

import com.pragma.emazon_stock.application.dto.article.SupplyRequest;
import com.pragma.emazon_stock.domain.model.Supply;
import com.pragma.emazon_stock.domain.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = Constants.SPRING_COMPONENT_MODEL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SupplyRequestMapper {

    @Mapping(target = "articleIds", source = "supplyArticleIds")
    @Mapping(target = "amounts", source = "supplyArticleAmounts")
    Supply toDomain(SupplyRequest supplyRequest);

}
