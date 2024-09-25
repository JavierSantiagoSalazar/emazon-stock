package com.pragma.emazon_stock.infrastructure.out.feing.adapter;

import com.pragma.emazon_stock.application.dto.transaction.SupplyTransactionRequest;
import com.pragma.emazon_stock.domain.model.SupplyTransaction;
import com.pragma.emazon_stock.domain.spi.FeignClientPort;
import com.pragma.emazon_stock.infrastructure.feing.TransactionFeignClient;
import com.pragma.emazon_stock.infrastructure.out.feing.mapper.SupplyTransactionRequestMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignClientAdapter implements FeignClientPort {

    private final TransactionFeignClient transactionFeignClient;
    private final SupplyTransactionRequestMapper supplyTransactionRequestMapper;

    @Override
    public void addNewRegisterFromStock(SupplyTransaction supplyTransaction) {
        SupplyTransactionRequest supplyTransactionRequest = supplyTransactionRequestMapper.toRequest(supplyTransaction);
        transactionFeignClient.addNewRegisterFromStock(supplyTransactionRequest);
    }

}
