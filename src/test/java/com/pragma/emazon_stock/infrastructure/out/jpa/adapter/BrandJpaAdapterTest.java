package com.pragma.emazon_stock.infrastructure.out.jpa.adapter;

import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.BrandEntity;
import com.pragma.emazon_stock.infrastructure.out.jpa.mapper.BrandEntityMapper;
import com.pragma.emazon_stock.infrastructure.out.jpa.repository.BrandRepository;
import com.pragma.emazon_stock.utils.ModelsTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BrandJpaAdapterTest {

    @Mock
    private BrandRepository brandRepository;
    @Mock
    private BrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandJpaAdapter brandJpaAdapter;

    private Brand defaultBrand;
    private BrandEntity defaultBrandEntity;

    @BeforeEach
    public void setUp() {

        defaultBrand = ModelsTestFactory.createDefaultBrand();
        defaultBrandEntity = ModelsTestFactory.createDefaultBrandEntity();

    }

    @Test
    void givenBrand_whenSaveBrandIsCalled_thenBrandIsSaved() {

        when(brandEntityMapper.toEntity(defaultBrand)).thenReturn(defaultBrandEntity);

        brandJpaAdapter.saveBrand(defaultBrand);

        verify(brandRepository, times(1)).save(defaultBrandEntity);
        verify(brandEntityMapper, times(1)).toEntity(defaultBrand);

    }

    @Test
    void givenBrandNameAlreadyExists_whenCheckIfBrandExists_thenReturnTrue() {

        when(brandRepository.findByBrandName(defaultBrand.getBrandName())).thenReturn(Optional.of(defaultBrandEntity));

        Boolean result = brandJpaAdapter.checkIfBrandExists(defaultBrand.getBrandName());

        verify(brandRepository, times(1)).findByBrandName(defaultBrand.getBrandName());

        assertTrue(result);

    }

    @Test
    void givenBrandNameDoesNotExist_whenCheckIfBrandExists_thenReturnFalse() {

        when(brandRepository.findByBrandName(defaultBrand.getBrandName())).thenReturn(Optional.empty());

        Boolean result = brandJpaAdapter.checkIfBrandExists(defaultBrand.getBrandName());

        verify(brandRepository, times(1)).findByBrandName(defaultBrand.getBrandName());

        assertFalse(result);

    }

}
