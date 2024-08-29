package com.pragma.emazon_stock.infrastructure.out.jpa.adapter;

import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.spi.BrandPersistencePort;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.BrandEntity;
import com.pragma.emazon_stock.infrastructure.out.jpa.mapper.BrandEntityMapper;
import com.pragma.emazon_stock.infrastructure.out.jpa.repository.BrandRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BrandJpaAdapter implements BrandPersistencePort {

    private final BrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    @Override
    public void saveBrand(Brand brand) {
        brandRepository.save(brandEntityMapper.toEntity(brand));
    }

    @Override
    public Boolean checkIfBrandExists(String brandName) {
        return brandRepository.findByBrandName(brandName).isPresent();
    }

    @Override
    public List<Brand> getAllBrands() {

        List<BrandEntity> brandEntityList = brandRepository.findAll();
        return brandEntityMapper.toBrandList(brandEntityList);
    }

}
