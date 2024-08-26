package com.pragma.emazon_stock.application.handler;

import com.pragma.emazon_stock.application.dto.CategoryRequest;
import com.pragma.emazon_stock.application.mappers.CategoryRequestMapper;
import com.pragma.emazon_stock.domain.api.CategoryServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class CategoryHandlerImpl implements CategoryHandler {

    private final CategoryServicePort categoryServicePort;
    private final CategoryRequestMapper categoryRequestMapper;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {

        categoryRequest.setName(categoryRequest.getName().trim().toUpperCase());
        categoryRequest.setDescription(categoryRequest.getDescription().trim());

        categoryServicePort.saveCategory(categoryRequestMapper.categotyRequestToDomainCategory(categoryRequest));

    }

}
