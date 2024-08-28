package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.exceptions.BrandAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.NoContentBrandException;
import com.pragma.emazon_stock.domain.exceptions.PageOutOfBoundsException;
import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.model.Pagination;
import com.pragma.emazon_stock.domain.spi.BrandPersistencePort;
import com.pragma.emazon_stock.utils.ModelsTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BrandUseCaseTest {

    @Mock
    private BrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    private Brand defaultBrand;

    @BeforeEach
    public void setUp() {
        defaultBrand = ModelsTestFactory.createDefaultBrand();
    }

    @Test
    void givenBrandDoesNotExist_whenSaveBrandIsCalled_thenBrandIsSaved() {

        when(brandPersistencePort.checkIfBrandExists(defaultBrand.getBrandName())).thenReturn(false);

        brandUseCase.saveBrand(defaultBrand);

        verify(brandPersistencePort, times(1)).checkIfBrandExists(defaultBrand.getBrandName());
        verify(brandPersistencePort, times(1)).saveBrand(defaultBrand);

    }

    @Test
    void givenBrandAlreadyExists_whenSaveBrandIsCalled_thenThrowsException() {

        when(brandPersistencePort.checkIfBrandExists(defaultBrand.getBrandName())).thenReturn(true);

        assertThrows(BrandAlreadyExistsException.class, () -> brandUseCase.saveBrand(defaultBrand));

        verify(brandPersistencePort, times(1)).checkIfBrandExists(defaultBrand.getBrandName());
        verify(brandPersistencePort, never()).saveBrand(defaultBrand);

    }

    @Test
    void whenGetBrands_ThenReturnBrandsWithSuccessfulPagination() {
        List<Brand> brands = Arrays.asList(
                new Brand(1,"Brand 1", "Description 1"),
                new Brand(2,"Brand 2", "Description 2"),
                new Brand(3,"Brand 3", "Description 3")
        );

        when(brandPersistencePort.getAllBrands()).thenReturn(brands);

        Pagination<Brand> result = brandUseCase.getBrands("asc", 1, 2);

        assertEquals(2, result.getItems().size());
        assertEquals(3, result.getTotalItems());
        assertEquals(2, result.getTotalPages());
        assertFalse(result.getIsLastPage());
        verify(brandPersistencePort, times(1)).getAllBrands();

    }

    @Test
    void givenBrands_whenGetBrands_ThenReturnsNoContent() {

        when(brandPersistencePort.getAllBrands()).thenReturn(Collections.emptyList());

        assertThrows(NoContentBrandException.class, () -> {
            brandUseCase.getBrands("asc", 1, 2);
        });

    }

    @Test
    void givenBrandsAndLargerPage_whenGetBrands_thenReturnsPageOutOfBounds() {

        List<Brand> brands = Arrays.asList(
                new Brand(2,"Brand 2", "Description 2"),
                new Brand(1,"Brand 1", "Description 1"),
                new Brand(3,"Brand 3", "Description 3")
        );

        when(brandPersistencePort.getAllBrands()).thenReturn(brands);

        assertThrows(PageOutOfBoundsException.class, () -> {
            brandUseCase.getBrands("asc", 5, 2);
        });

    }

    @Test
    void givenBrandsAndDescOrder_whenGetBrands_thenReturnsBrandsListInDescendingOrder() {

        List<Brand> brands = Arrays.asList(
                new Brand(1, "Brand 1", "Description 1"),
                new Brand(2, "Brand 2", "Description 2"),
                new Brand(3, "Brand 3", "Description 3")
        );

        when(brandPersistencePort.getAllBrands()).thenReturn(brands);

        Pagination<Brand> result = brandUseCase.getBrands("desc", 1, 2);

        assertEquals("Brand 3", result.getItems().get(0).getBrandName());
        assertEquals("Brand 2", result.getItems().get(1).getBrandName());
        verify(brandPersistencePort, times(1)).getAllBrands();

    }

}
