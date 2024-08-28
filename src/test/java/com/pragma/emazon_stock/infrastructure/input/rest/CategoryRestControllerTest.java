package com.pragma.emazon_stock.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.emazon_stock.application.dto.category.CategoryRequest;
import com.pragma.emazon_stock.application.dto.category.CategoryResponse;
import com.pragma.emazon_stock.application.handler.category.CategoryHandler;
import com.pragma.emazon_stock.domain.exceptions.CategoryAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.PageOutOfBoundsException;
import com.pragma.emazon_stock.domain.model.Pagination;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private CategoryResponse categoryResponse;

    @BeforeEach
    public void setUp() {

        categoryRequest = new CategoryRequest();
        categoryRequest.setName("HOME");
        categoryRequest.setDescription("All things related with home");

        categoryResponse = new CategoryResponse();
        categoryResponse.setId(1);
        categoryResponse.setName("HOME");
        categoryResponse.setDescription("All things related with home");

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

    @Test
    void givenCategoryNameExceedsMaxLength_whenCreateCategory_thenReturns400() throws Exception {

        categoryRequest.setName("A".repeat(51));

        mockMvc.perform(post("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(
                        "[The length of the name cannot exceed 50 characters]"
                ));

    }

    @Test
    void givenCategoryDescriptionExceedsMaxLength_whenCreateCategory_thenReturns400() throws Exception {

        categoryRequest.setDescription("B".repeat(91));

        mockMvc.perform(post("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(
                        "[The length of the description cannot exceed 90 characters]"
                ));

    }

    @Test
    void givenCategoryNameAndDescriptionExceedMaxLength_whenCreateCategory_thenReturns400() throws Exception {

        categoryRequest.setName("A".repeat(51));
        categoryRequest.setDescription("B".repeat(91));

        mockMvc.perform(post("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(containsString("The length of the name cannot exceed 50 characters")))
                .andExpect(jsonPath("$.message").value(containsString("The length of the description cannot exceed 90 characters")));

    }

    @Test
    void givenValidPagination_whenGetCategories_thenReturns200() throws Exception {

        Pagination<CategoryResponse> paginationResponse = new Pagination<>(
                List.of(categoryResponse),
                1,
                10,
                1L,
                1,
                true
        );

        when(categoryHandler.getCategories("asc", 1, 10)).thenReturn(paginationResponse);

        mockMvc.perform(get("/category/")
                        .param("sortOrder", "asc")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items[0].name").value("HOME"))
                .andExpect(jsonPath("$.items[0].description").value("All things related with home"))
                .andExpect(jsonPath("$.pageNo").value(1))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalItems").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.isLastPage").value(true));

    }

    @Test
    void givenAscSortOrder_whenGetCategories_ReturnsCategoriesInAscendingOrder() throws Exception {

        CategoryResponse booksCategory = new CategoryResponse();
        booksCategory.setName("BOOKS");
        booksCategory.setDescription("All kinds of books");

        Pagination<CategoryResponse> paginationResponse = new Pagination<>(
                List.of(
                        booksCategory,
                        this.categoryResponse
                ),
                1,
                10,
                2L,
                1,
                true
        );

        when(categoryHandler.getCategories("asc", 1, 10)).thenReturn(paginationResponse);

        mockMvc.perform(get("/category/")
                        .param("sortOrder", "asc")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").value("BOOKS"))
                .andExpect(jsonPath("$.items[1].name").value("HOME"));

    }

    @Test
    void givenDescSortOrder_whenGetCategories_ReturnsCategoriesInDescendingOrder() throws Exception {

        CategoryResponse wallpaperCategory = new CategoryResponse();
        wallpaperCategory.setName("WALLPAPER");
        wallpaperCategory.setDescription("AAll wallpaper items");

        Pagination<CategoryResponse> paginationResponse = new Pagination<>(
                List.of(
                        wallpaperCategory,
                        this.categoryResponse
                ),
                1,
                10,
                2L,
                1,
                true
        );

        when(categoryHandler.getCategories("desc", 1, 10)).thenReturn(paginationResponse);

        mockMvc.perform(get("/category/")
                        .param("sortOrder", "desc")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").value("WALLPAPER"))
                .andExpect(jsonPath("$.items[1].name").value("HOME"));

    }

    @Test
    void givenBadRequest_whenGetCategories_PageOutOfBounds_ReturnsBadRequest() throws Exception {

        Integer requestedPage = 10;
        Integer totalPages = 5;

        when(categoryHandler.getCategories("asc", requestedPage, 10))
                .thenThrow(new PageOutOfBoundsException(requestedPage, totalPages));

        mockMvc.perform(get("/category/")
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
