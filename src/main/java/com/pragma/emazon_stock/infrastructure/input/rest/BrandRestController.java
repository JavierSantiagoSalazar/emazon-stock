package com.pragma.emazon_stock.infrastructure.input.rest;

import com.pragma.emazon_stock.application.dto.brand.BrandRequest;
import com.pragma.emazon_stock.application.dto.brand.BrandResponse;
import com.pragma.emazon_stock.application.handler.brand.BrandHandler;
import com.pragma.emazon_stock.domain.model.Pagination;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestController {

    private final BrandHandler brandHandler;

    @Operation(summary = "Add new brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Brand already exists", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> createBrand(@Valid @RequestBody BrandRequest brandRequest) {
        brandHandler.createBrand(brandRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{brandName}")
                .buildAndExpand(brandRequest.getBrandName())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get all brands")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brands obtained",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BrandResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "400", description = "The page requested is out of range", content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<Pagination<BrandResponse>> getBrands(
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(brandHandler.getBrands(sortOrder, page, size));
    }

}
