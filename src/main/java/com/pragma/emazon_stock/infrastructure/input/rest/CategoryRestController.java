package com.pragma.emazon_stock.infrastructure.input.rest;

import com.pragma.emazon_stock.application.dto.category.CategoryRequest;
import com.pragma.emazon_stock.application.dto.category.CategoryResponse;
import com.pragma.emazon_stock.application.handler.category.CategoryHandler;
import com.pragma.emazon_stock.domain.model.Pagination;
import com.pragma.emazon_stock.domain.utils.Constants;
import com.pragma.emazon_stock.domain.utils.HttpStatusCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestController {

    @Value("${category.name.path}")
    private String categoryNamePath;

    private final CategoryHandler categoryHandler;

    @Operation(summary = Constants.ADD_NEW_CATEGORY_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCode.CREATED, description = Constants.CATEGORY_CREATED, content = @Content),
            @ApiResponse(responseCode = HttpStatusCode.CONFLICT, description = Constants.CATEGORY_ALREADY_EXISTS, content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {

        categoryHandler.createCategory(categoryRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(categoryNamePath)
                .buildAndExpand(categoryRequest.getName())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = Constants.GET_ALL_CATEGORIES_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCode.OK, description = Constants.CATEGORIES_OBTAINED,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))
                    )
            ),
            @ApiResponse(responseCode = HttpStatusCode.BAD_REQUEST, description = Constants.PAGE_OUT_OF_RANGE, content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<Pagination<CategoryResponse>> getCategories(
            @RequestParam(defaultValue = Constants.ASC_SORT_ORDER) String sortOrder,
            @RequestParam(defaultValue = Constants.PAGE_DEFAULT_VALUE) Integer page,
            @RequestParam(defaultValue = Constants.SIZE_DEFAULT_VALUE) Integer size
        ) {
        return ResponseEntity.ok(categoryHandler.getCategories(sortOrder, page, size));
    }

}
