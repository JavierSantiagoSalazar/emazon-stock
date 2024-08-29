package com.pragma.emazon_stock.application.dto.category;

import com.pragma.emazon_stock.infrastructure.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    @NotBlank(message = Constants.CATEGORY_NAME_MUST_NOT_BE_BLANK)
    @NotNull(message = Constants.CATEGORY_NAME_MUST_NOT_BE_NULL)
    @Size(max = 50, message = Constants.CATEGORY_NAME_LENGTH_EXCEEDED)
    private String name;

    @NotBlank(message = Constants.CATEGORY_DESCRIPTION_MUST_NOT_BE_BLANK)
    @NotNull(message = Constants.CATEGORY_DESCRIPTION_MUST_NOT_BE_NULL)
    @Size(max = 90, message = Constants.CATEGORY_DESCRIPTION_LENGTH_EXCEEDED)
    private String description;

}
