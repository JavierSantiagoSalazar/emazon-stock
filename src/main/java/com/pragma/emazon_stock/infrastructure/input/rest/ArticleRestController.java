package com.pragma.emazon_stock.infrastructure.input.rest;

import com.pragma.emazon_stock.application.dto.article.ArticleRequest;
import com.pragma.emazon_stock.application.handler.article.ArticleHandler;
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
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleRestController {

    private final ArticleHandler articleHandler;

    @Operation(summary = "Add new article")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Article created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Article already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Article has duplicated names in the article categories", content = @Content),
            @ApiResponse(responseCode = "400", description = "Article categories must be between 1 and 3", content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Void> createArticle(@Valid @RequestBody ArticleRequest articleRequest) {

        articleHandler.createArticle(articleRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{articleName}")
                .buildAndExpand(articleRequest.getArticleName())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
