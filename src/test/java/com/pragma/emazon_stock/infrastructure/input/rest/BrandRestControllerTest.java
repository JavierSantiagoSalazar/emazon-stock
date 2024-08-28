package com.pragma.emazon_stock.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.emazon_stock.application.dto.BrandRequest;
import com.pragma.emazon_stock.application.handler.brand.BrandHandler;
import com.pragma.emazon_stock.domain.exceptions.BrandAlreadyExistsException;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
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

    @Autowired
    private ObjectMapper objectMapper;

    private BrandRequest brandRequest;

    @BeforeEach
    public void setUp() {

        brandRequest = new BrandRequest();
        brandRequest.setBrandName("NOKIA");
        brandRequest.setBrandDescription("All nokia tech");

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

}
