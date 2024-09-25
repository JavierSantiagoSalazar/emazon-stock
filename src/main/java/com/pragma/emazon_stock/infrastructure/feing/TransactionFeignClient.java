package com.pragma.emazon_stock.infrastructure.feing;

import com.pragma.emazon_stock.application.dto.transaction.SupplyTransactionRequest;
import com.pragma.emazon_stock.domain.utils.Constants;
import com.pragma.emazon_stock.infrastructure.configuration.bean.ClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = Constants.TRANSACTION_MICROSERVICE_NAME,
        url = "${emazon_transaction.url}",
        configuration = ClientConfiguration.class
)
public interface TransactionFeignClient {

    @PostMapping(value = "/add-new-register")
    void addNewRegisterFromStock(@RequestBody SupplyTransactionRequest supplyTransactionRequest);

}
