package com.pragma.emazon_stock.application.dto.category;

import com.pragma.emazon_stock.domain.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    private @NotBlank(message = Constants.CATEGORY_NAME_MUST_NOT_BE_BLANK)
    @NotNull(message = Constants.CATEGORY_NAME_MUST_NOT_BE_NULL)
    @Size(max = Constants.CATEGORY_NAME_LENGTH, message = Constants.CATEGORY_NAME_LENGTH_EXCEEDED)
    String name;

    private @NotBlank(message = Constants.CATEGORY_DESCRIPTION_MUST_NOT_BE_BLANK)
    @NotNull(message = Constants.CATEGORY_DESCRIPTION_MUST_NOT_BE_NULL)
    @Size(max = Constants.CATEGORY_DESCRIPTION_LENGTH, message = Constants.CATEGORY_DESCRIPTION_LENGTH_EXCEEDED)
    String description;

}
