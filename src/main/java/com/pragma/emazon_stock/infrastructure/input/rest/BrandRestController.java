package com.pragma.emazon_stock.infrastructure.input.rest;

import com.pragma.emazon_stock.application.dto.BrandRequest;
import com.pragma.emazon_stock.application.handler.brand.BrandHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

}
