package com.pragma.emazon_stock.application.dto.brand;

import com.pragma.emazon_stock.infrastructure.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {

    @NotBlank(message = Constants.BRAND_NAME_MUST_NOT_BE_BLANK)
    @NotNull(message = Constants.BRAND_NAME_MUST_NOT_BE_NULL)
    @Size(max = 50, message = Constants.BRAND_NAME_LENGTH_EXCEEDED)
    private String brandName;

    @NotBlank(message = Constants.BRAND_DESCRIPTION_MUST_NOT_BE_BLANK)
    @NotNull(message = Constants.BRAND_DESCRIPTION_MUST_NOT_BE_NULL)
    @Size(max = 120, message = Constants.BRAND_DESCRIPTION_LENGTH_EXCEEDED)
    private String brandDescription;

}
