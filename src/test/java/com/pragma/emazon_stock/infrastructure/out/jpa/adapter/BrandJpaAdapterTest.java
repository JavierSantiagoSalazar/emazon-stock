package com.pragma.emazon_stock.infrastructure.out.jpa.adapter;

import com.pragma.emazon_stock.domain.exceptions.BrandDoesNotExistException;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    @Test
    void whenGetAllBrands_thenReturnBrandsList() {

        BrandEntity brandEntity = new BrandEntity(1, "NOKIA", "Description", null);
        List<BrandEntity> brandEntityList = List.of(brandEntity);

        Brand brand = new Brand(1, "NOKIA", "Description", null);
        List<Brand> brandList = List.of(brand);

        when(brandRepository.findAll()).thenReturn(brandEntityList);
        when(brandEntityMapper.toBrandList(brandEntityList)).thenReturn(brandList);

        List<Brand> result = brandJpaAdapter.getAllBrands();

        assertEquals(1, result.size());
        assertEquals("NOKIA", result.get(0).getBrandName());
        assertEquals("Description", result.get(0).getBrandDescription());

        verify(brandRepository, times(1)).findAll();

        verify(brandEntityMapper, times(1)).toBrandList(brandEntityList);
    }

    @Test
    void givenExistingBrandName_whenGetBrandByName_thenReturnBrand() {

        String brandName = "NOKIA";

        when(brandRepository.findByBrandName(brandName)).thenReturn(Optional.of(defaultBrandEntity));
        when(brandEntityMapper.toDomain(defaultBrandEntity)).thenReturn(defaultBrand);

        Brand result = brandJpaAdapter.getBrandByName(brandName);

        assertEquals(defaultBrand, result);
        verify(brandRepository, times(1)).findByBrandName(brandName);
        verify(brandEntityMapper, times(1)).toDomain(defaultBrandEntity);
    }

    @Test
    void givenNonExistingBrandName_whenGetBrandByName_thenThrowBrandDoesNotExistException() {

        String brandName = "UNKNOWN";

        when(brandRepository.findByBrandName(brandName)).thenReturn(Optional.empty());

        BrandDoesNotExistException exception = assertThrows(
                BrandDoesNotExistException.class,
                () -> brandJpaAdapter.getBrandByName(brandName)
        );
        assertEquals("The requested brand does not exist, requested brand: UNKNOWN", exception.getMessage());

        verify(brandRepository, times(1)).findByBrandName(brandName);
        verify(brandEntityMapper, times(0)).toDomain(any());
    }

}
