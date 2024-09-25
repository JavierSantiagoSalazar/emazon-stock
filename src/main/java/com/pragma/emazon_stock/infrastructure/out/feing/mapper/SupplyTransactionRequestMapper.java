package com.pragma.emazon_stock.infrastructure.out.feing.mapper;

import com.pragma.emazon_stock.application.dto.transaction.SupplyTransactionRequest;
import com.pragma.emazon_stock.domain.model.SupplyTransaction;
import com.pragma.emazon_stock.domain.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = Constants.SPRING_COMPONENT_MODEL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SupplyTransactionRequestMapper {

    SupplyTransactionRequest toRequest(SupplyTransaction supplyTransaction);

}


