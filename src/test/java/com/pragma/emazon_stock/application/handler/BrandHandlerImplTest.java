package com.pragma.emazon_stock.application.handler;

import com.pragma.emazon_stock.application.dto.BrandRequest;
import com.pragma.emazon_stock.application.handler.brand.BrandHandlerImpl;
import com.pragma.emazon_stock.application.mappers.brand.BrandRequestMapper;
import com.pragma.emazon_stock.domain.api.BrandServicePort;
import com.pragma.emazon_stock.domain.model.Brand;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
class BrandHandlerImplTest {

    @Mock
    private BrandServicePort brandServicePort;

    @Mock
    private BrandRequestMapper brandRequestMapper;

    @InjectMocks
    private BrandHandlerImpl brandHandlerImpl;

    @Test
    void givenValidBrandRequest_whenCreateBrand_thenBrandIsSaved() {

        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setBrandName("  Nokia  ");
        brandRequest.setBrandDescription("  All nokia tech ");

        Brand mappedBrand = new Brand(null, "NOKIA", "All nokia tech");

        when(brandRequestMapper.toDomain(brandRequest)).thenReturn(mappedBrand);

        brandHandlerImpl.createBrand(brandRequest);

        verify(brandRequestMapper,times(1)).toDomain(brandRequest);
        verify(brandServicePort, times(1)).saveBrand(mappedBrand);

    }

}
