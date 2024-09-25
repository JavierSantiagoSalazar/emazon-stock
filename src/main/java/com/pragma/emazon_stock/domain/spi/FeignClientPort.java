package com.pragma.emazon_stock.domain.spi;

import com.pragma.emazon_stock.domain.model.SupplyTransaction;

public interface FeignClientPort {

    void addNewRegisterFromStock(SupplyTransaction supplyTransaction);

}
