package com.pragma.emazon_stock.infrastructure.out.feing.adapter;

import com.pragma.emazon_stock.application.dto.transaction.SupplyTransactionRequest;
import com.pragma.emazon_stock.domain.model.SupplyTransaction;
import com.pragma.emazon_stock.infrastructure.feing.TransactionFeignClient;
import com.pragma.emazon_stock.infrastructure.out.feing.mapper.SupplyTransactionRequestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeignClientAdapterTest {

    @Mock
    private TransactionFeignClient transactionFeignClient;

    @Mock
    private SupplyTransactionRequestMapper supplyTransactionRequestMapper;

    @InjectMocks
    private FeignClientAdapter feignClientAdapter;

    private SupplyTransaction supplyTransaction;
    private SupplyTransactionRequest supplyTransactionRequest;

    @BeforeEach
    void setUp() {

        supplyTransaction = new SupplyTransaction(1, 10);

        supplyTransactionRequest = new SupplyTransactionRequest(1, 10);
    }

    @Test
    void givenValidSupplyTransaction_whenAddNewRegisterFromStockIsCalled_thenFeignClientIsInvoked() {

        when(supplyTransactionRequestMapper.toRequest(supplyTransaction)).thenReturn(supplyTransactionRequest);

        feignClientAdapter.addNewRegisterFromStock(supplyTransaction);

        verify(supplyTransactionRequestMapper).toRequest(supplyTransaction);

        verify(transactionFeignClient).addNewRegisterFromStock(supplyTransactionRequest);
    }

    @Test
    void givenSupplyTransactionWithInvalidData_whenAddNewRegisterFromStock_thenMapperAndFeignClientAreStillCalled() {

        supplyTransaction.setArticleAmount(-5);

        when(supplyTransactionRequestMapper.toRequest(supplyTransaction)).thenReturn(supplyTransactionRequest);

        feignClientAdapter.addNewRegisterFromStock(supplyTransaction);

        verify(supplyTransactionRequestMapper).toRequest(supplyTransaction);
        verify(transactionFeignClient).addNewRegisterFromStock(supplyTransactionRequest);
    }

}
