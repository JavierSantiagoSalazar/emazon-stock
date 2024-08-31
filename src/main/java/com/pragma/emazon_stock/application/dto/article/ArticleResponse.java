package com.pragma.emazon_stock.application.dto.article;

import com.pragma.emazon_stock.application.dto.brand.BrandResponse;
import com.pragma.emazon_stock.application.dto.category.EmbeddedCategoryResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleResponse {

    private Integer articleId;
    private String articleName;
    private String articleDescription;
    private Integer articleAmount;
    private Double articlePrice;
    private BrandResponse articleBrand;
    private List<EmbeddedCategoryResponse> articleCategories;

}
