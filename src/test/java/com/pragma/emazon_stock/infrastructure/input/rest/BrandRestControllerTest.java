package com.pragma.emazon_stock.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.emazon_stock.application.dto.brand.BrandRequest;
import com.pragma.emazon_stock.application.dto.brand.BrandResponse;
import com.pragma.emazon_stock.application.handler.brand.BrandHandler;
import com.pragma.emazon_stock.domain.exceptions.BrandAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.PageOutOfBoundsException;
import com.pragma.emazon_stock.domain.model.Pagination;
import com.pragma.emazon_stock.infrastructure.configuration.security.filter.JwtValidatorFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BrandRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BrandRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandHandler brandHandler;

    @MockBean
    private JwtValidatorFilter jwtValidatorFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private BrandRequest brandRequest;
    private BrandResponse brandResponse;

    @BeforeEach
    public void setUp() {

        brandRequest = new BrandRequest();
        brandRequest.setBrandName("NOKIA");
        brandRequest.setBrandDescription("All nokia tech");

        brandResponse = new BrandResponse();
        brandResponse.setBrandId(1);
        brandResponse.setBrandName("NOKIA");
        brandResponse.setBrandDescription("All nokia tech");

    }

    @Test
    void givenValidBrandRequest_whenCreateBrandIsCalled_thenReturns201() throws  Exception{

        doNothing().when(brandHandler).createBrand(brandRequest);

        mockMvc.perform(post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));
    }

    @Test
    void givenInvalidBrandRequest_whenCreateBrand_thenReturns400() throws Exception {

        brandRequest.setBrandName("");
        brandRequest.setBrandDescription("");


        mockMvc.perform(post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenDuplicateBrand_whenCreateBrand_thenReturns409() throws Exception {

        doThrow(new BrandAlreadyExistsException()).when(brandHandler).createBrand(any(BrandRequest.class));

        mockMvc.perform(post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void givenBrandNameExceedsMaxLength_whenCreateBrand_thenReturns400() throws Exception {

        brandRequest.setBrandName("A".repeat(51));

        mockMvc.perform(post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(
                        "[The length of the name cannot exceed 50 characters]"
                ));
    }

    @Test
    void givenBrandDescriptionExceedsMaxLength_whenCreateBrand_thenReturns400() throws Exception {

        brandRequest.setBrandDescription("B".repeat(121));

        mockMvc.perform(post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(
                        "[The length of the description cannot exceed 120 characters]"
                ));
    }

    @Test
    void givenBrandNameAndDescriptionExceedMaxLength_whenCreateBrand_thenReturns400() throws Exception {

        brandRequest.setBrandName("A".repeat(51));
        brandRequest.setBrandDescription("B".repeat(121));

        mockMvc.perform(post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(containsString("The length of the name cannot exceed 50 characters")))
                .andExpect(jsonPath("$.message").value(containsString("The length of the description cannot exceed 120 characters")));
    }


    @Test
    void givenValidPagination_whenGetBrands_thenReturns200() throws Exception {

        Pagination<BrandResponse> paginationResponse = new Pagination<>(
                List.of(brandResponse),
                1,
                10,
                1L,
                1,
                true
        );

        when(brandHandler.getBrands("asc", 1, 10)).thenReturn(paginationResponse);

        mockMvc.perform(get("/brand/")
                        .param("sortOrder", "asc")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items[0].brandName").value("NOKIA"))
                .andExpect(jsonPath("$.items[0].brandDescription").value("All nokia tech"))
                .andExpect(jsonPath("$.pageNo").value(1))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalItems").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.isLastPage").value(true));
    }

    @Test
    void givenAscSortOrder_whenGetBrands_ReturnsBrandsInAscendingOrder() throws Exception {

        BrandResponse booksBrand = new BrandResponse();
        booksBrand.setBrandName("HP");
        booksBrand.setBrandDescription("All hp tech");

        Pagination<BrandResponse> paginationResponse = new Pagination<>(
                List.of(
                        booksBrand,
                        this.brandResponse
                ),
                1,
                10,
                2L,
                1,
                true
        );

        when(brandHandler.getBrands("asc", 1, 10)).thenReturn(paginationResponse);

        mockMvc.perform(get("/brand/")
                        .param("sortOrder", "asc")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].brandName").value("HP"))
                .andExpect(jsonPath("$.items[1].brandName").value("NOKIA"));
    }

    @Test
    void givenDescSortOrder_whenGetBrands_ReturnsBrandsInDescendingOrder() throws Exception {

        BrandResponse nokiaBrand = new BrandResponse();
        nokiaBrand.setBrandName("HP");
        nokiaBrand.setBrandDescription("All hp items");

        Pagination<BrandResponse> paginationResponse = new Pagination<>(
                List.of(
                        this.brandResponse,
                        nokiaBrand
                ),
                1,
                10,
                2L,
                1,
                true
        );

        when(brandHandler.getBrands("desc", 1, 10)).thenReturn(paginationResponse);

        mockMvc.perform(get("/brand/")
                        .param("sortOrder", "desc")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].brandName").value("NOKIA"))
                .andExpect(jsonPath("$.items[1].brandName").value("HP"));
    }

    @Test
    void givenBadRequest_whenGetBrands_PageOutOfBounds_ReturnsBadRequest() throws Exception {

        Integer requestedPage = 10;
        Integer totalPages = 5;

        when(brandHandler.getBrands("asc", requestedPage, 10))
                .thenThrow(new PageOutOfBoundsException(requestedPage, totalPages));

        mockMvc.perform(get("/brand/")
                        .param("sortOrder", "asc")
                        .param("page", String.valueOf(requestedPage))
                        .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response.statusCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.response.message").value("Page 10 is out of range. Total pages: 5"))
                .andExpect(jsonPath("$.pageNo").value(requestedPage))
                .andExpect(jsonPath("$.totalPages").value(totalPages));
    }

}
