package com.pragma.emazon_stock.application.dto.article;

import com.pragma.emazon_stock.domain.utils.Constants;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SupplyRequest {

    private List<@NotNull(message = Constants.ARTICLE_ID_MUST_NOT_BE_NULL)
                @Positive(message = Constants.ARTICLE_ID_MUST_BE_POSITIVE)
                    Integer> supplyArticleIds;


    private List<@NotNull(message = Constants.ARTICLE_AMOUNT_MUST_NOT_BE_NULL)
                @Positive(message = Constants.ARTICLE_AMOUNT_MUST_BE_POSITIVE)
                    Integer> supplyArticleAmounts;

}
