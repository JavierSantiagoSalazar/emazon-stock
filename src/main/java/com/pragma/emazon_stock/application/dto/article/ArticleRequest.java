package com.pragma.emazon_stock.application.dto.article;

import com.pragma.emazon_stock.infrastructure.utils.Constants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ArticleRequest {

    @NotBlank(message = Constants.ARTICLE_NAME_MUST_NOT_BE_BLANK)
    @NotNull(message = Constants.ARTICLE_NAME_MUST_NOT_BE_NULL)
    @Size(max = 120, message = Constants.ARTICLE_NAME_LENGTH_EXCEEDED)
    private String articleName;

    @NotBlank(message = Constants.ARTICLE_DESCRIPTION_MUST_NOT_BE_BLANK)
    @NotNull(message = Constants.ARTICLE_DESCRIPTION_MUST_NOT_BE_NULL)
    @Size(max = 160, message = Constants.ARTICLE_DESCRIPTION_LENGTH_EXCEEDED)
    private String articleDescription;

    @NotNull(message = Constants.ARTICLE_AMOUNT_MUST_NOT_BE_NULL)
    @PositiveOrZero(message = Constants.ARTICLE_AMOUNT_CANNOT_BE_NEGATIVE)
    private Integer articleAmount;

    @NotNull(message = Constants.ARTICLE_PRICE_MUST_NOT_BE_NULL)
    @Positive(message = Constants.ARTICLE_PRICE_CANNOT_BE_NEGATIVE)
    @DecimalMin(value = "0.01", message = Constants.ARTICLE_PRICE_MINIMAL_VALUE)
    private Double articlePrice;

    @NotNull(message = Constants.ARTICLE_CATEGORIES_MUST_NOT_BE_NULL)
    private List<String> articleCategories;

}
