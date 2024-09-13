package com.pragma.emazon_stock.application.handler;

import com.pragma.emazon_stock.application.dto.brand.BrandRequest;
import com.pragma.emazon_stock.application.dto.brand.BrandResponse;
import com.pragma.emazon_stock.application.handler.brand.BrandHandlerImpl;
import com.pragma.emazon_stock.application.mappers.brand.BrandRequestMapper;
import com.pragma.emazon_stock.application.mappers.brand.BrandResponseMapper;
import com.pragma.emazon_stock.domain.api.BrandServicePort;
import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.model.Pagination;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BrandHandlerImplTest {

    @Mock
    private BrandServicePort brandServicePort;

    @Mock
    private BrandRequestMapper brandRequestMapper;

    @Mock
    private BrandResponseMapper brandResponseMapper;

    @InjectMocks
    private BrandHandlerImpl brandHandlerImpl;

    @Test
    void givenValidBrandRequest_whenCreateBrand_thenBrandIsSaved() {

        BrandRequest brandRequest = new BrandRequest();
        brandRequest.setBrandName("  Nokia  ");
        brandRequest.setBrandDescription("  All nokia tech ");

        Brand mappedBrand = new Brand(null, "NOKIA", "All nokia tech", new ArrayList<>());

        when(brandRequestMapper.toDomain(brandRequest)).thenReturn(mappedBrand);

        brandHandlerImpl.createBrand(brandRequest);

        verify(brandRequestMapper,times(1)).toDomain(brandRequest);
        verify(brandServicePort, times(1)).saveBrand(mappedBrand);

    }

    @Test
    void givenValidRequest_whenGetBrands_thenReturnsBrandsList() {

        String sortOrder = "asc";
        Integer page = 1;
        Integer size = 10;

        Brand brand = new Brand(1,"NOKIA", "All nokia tech", new ArrayList<>());
        List<Brand> brandList = List.of(brand);

        Pagination<Brand> pagination = new Pagination<>(
                brandList,
                page,
                size,
                1L,
                1,
                true
        );

        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setBrandId(1);
        brandResponse.setBrandName("NOKIA");
        brandResponse.setBrandDescription("All nokia tech");

        when(brandServicePort.getBrands(sortOrder, page, size)).thenReturn(pagination);
        when(brandResponseMapper.toResponse(brand)).thenReturn(brandResponse);

        Pagination<BrandResponse> result = brandHandlerImpl.getBrands(sortOrder, page, size);

        assertEquals(1, result.getItems().size());
        assertEquals("NOKIA", result.getItems().get(0).getBrandName());
        assertEquals(page, result.getPageNo());
        assertEquals(size, result.getPageSize());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.getIsLastPage());

        verify(brandServicePort, times(1)).getBrands(sortOrder, page, size);
        verify(brandResponseMapper, times(1)).toResponse(brand);

    }

}
