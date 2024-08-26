package com.pragma.emazon_stock.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.emazon_stock.application.dto.CategoryRequest;
import com.pragma.emazon_stock.application.handler.CategoryHandler;
import com.pragma.emazon_stock.domain.exceptions.CategoryAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CategoryRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryHandler categoryHandler;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryRequest categoryRequest;

    @BeforeEach
    public void setUp() {
        categoryRequest = new CategoryRequest();
        categoryRequest.setName("HOME");
        categoryRequest.setDescription("All things related with home");
    }

    @Test
    void givenValidCategoryRequest_whenCreateCategoryIsCalled_thenReturns201() throws  Exception{

        doNothing().when(categoryHandler).createCategory(categoryRequest);

        mockMvc.perform(post("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));

    }

    @Test
    void givenInvalidCategoryRequest_whenCreateCategory_thenReturns400() throws Exception {

        categoryRequest.setName("");
        categoryRequest.setDescription("");


        mockMvc.perform(post("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void givenDuplicateCategory_whenCreateCategory_thenReturns409() throws Exception {

        doThrow(new CategoryAlreadyExistsException()).when(categoryHandler).createCategory(any(CategoryRequest.class));

        mockMvc.perform(post("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isConflict());

    }

}
