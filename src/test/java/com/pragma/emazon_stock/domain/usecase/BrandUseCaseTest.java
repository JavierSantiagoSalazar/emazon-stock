package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.exceptions.BrandAlreadyExistsException;
import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.spi.BrandPersistencePort;
import com.pragma.emazon_stock.utils.ModelsTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

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

}
