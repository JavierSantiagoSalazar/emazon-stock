package com.pragma.emazon_stock.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.emazon_stock.application.dto.article.ArticleRequest;
import com.pragma.emazon_stock.application.dto.article.ArticleResponse;
import com.pragma.emazon_stock.application.dto.article.SupplyRequest;
import com.pragma.emazon_stock.application.dto.brand.BrandResponse;
import com.pragma.emazon_stock.application.dto.category.EmbeddedCategoryResponse;
import com.pragma.emazon_stock.application.handler.article.ArticleHandler;
import com.pragma.emazon_stock.domain.exceptions.ArticleAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.ArticleNotFoundException;
import com.pragma.emazon_stock.domain.exceptions.InvalidFilteringParameterException;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ArticleRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ArticleRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleHandler articleHandler;

    @MockBean
    private JwtValidatorFilter jwtValidatorFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private ArticleRequest articleRequest;
    private ArticleResponse articleResponse;

    @BeforeEach
    public void setUp() {

        articleRequest = new ArticleRequest();
        articleRequest.setArticleName("iPhone 13");
        articleRequest.setArticleDescription("Apple smartphone with advanced features");
        articleRequest.setArticleAmount(100);
        articleRequest.setArticlePrice(999.99);
        articleRequest.setArticleCategories(Arrays.asList("Technology", "Mobile"));
        articleRequest.setArticleBrand("Apple");

        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setBrandName("Apple");

        EmbeddedCategoryResponse embeddedCategoryResponse = new EmbeddedCategoryResponse(1,"PHONES");

        articleResponse = new ArticleResponse();
        articleResponse.setArticleId(1);
        articleResponse.setArticleName("IPhone 13");
        articleResponse.setArticleDescription("Apple smartphone with advanced features");
        articleResponse.setArticleAmount(100);
        articleResponse.setArticlePrice(999.99);
        articleResponse.setArticleBrand(brandResponse);
        articleResponse.setArticleCategories(List.of(embeddedCategoryResponse));
    }

    @Test
    void givenValidArticleRequest_whenCreateArticleIsCalled_thenReturns201() throws Exception {

        doNothing().when(articleHandler).createArticle(articleRequest);

        mockMvc.perform(post("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"));
    }

    @Test
    void givenInvalidArticleName_whenCreateArticle_thenReturns400() throws Exception {

        articleRequest.setArticleName("");

        mockMvc.perform(post("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenDuplicateArticle_whenCreateArticle_thenReturns409() throws Exception {

        doThrow(new ArticleAlreadyExistsException()).when(articleHandler).createArticle(any(ArticleRequest.class));

        mockMvc.perform(post("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void givenArticleNameExceedsMaxLength_whenCreateArticle_thenReturns400() throws Exception {

        articleRequest.setArticleName("A".repeat(121));

        mockMvc.perform(post("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(
                        "[The length of the name cannot exceed 120 characters]"
                ));
    }

    @Test
    void givenArticleDescriptionExceedsMaxLength_whenCreateArticle_thenReturns400() throws Exception {

        articleRequest.setArticleDescription("B".repeat(161));

        mockMvc.perform(post("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(
                        "[The length of the description cannot exceed 160 characters]"
                ));
    }

    @Test
    void givenNegativeAndLowerAmount_whenCreateArticle_thenReturns400() throws Exception {

        articleRequest.setArticleAmount(-10);

        mockMvc.perform(post("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(
                        "[The article amount can not be negative]"
                ));
    }

    @Test
    void givenNegativeAndLowerPrice_whenCreateArticle_thenReturns400() throws Exception {

        articleRequest.setArticlePrice(-10.0);

        mockMvc.perform(post("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("The article price can not be negative")))
                .andExpect(jsonPath("$.message").value(containsString("The article price can not be lower than 0.01")));
    }

    @Test
    void givenArticleCompleteWrong_whenCreateArticle_thenReturns400() throws Exception {

        articleRequest.setArticleName("A".repeat(121));
        articleRequest.setArticleDescription("B".repeat(161));
        articleRequest.setArticleAmount(-10);
        articleRequest.setArticlePrice(-10.0);

        mockMvc.perform(post("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(articleRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message").value(containsString("The length of the name cannot exceed 120 characters")))
                .andExpect(jsonPath("$.message").value(containsString("The length of the description cannot exceed 160 characters")))
                .andExpect(jsonPath("$.message").value(containsString("The article amount can not be negative")))
                .andExpect(jsonPath("$.message").value(containsString("The article price can not be negative")))
                .andExpect(jsonPath("$.message").value(containsString("The article price can not be lower than 0.01")));
    }
    @Test
    void givenValidPaginationAndSortedAscAndFilterByArticles_whenGetArticles_thenReturns200AndSortedArticlesList() throws Exception {

        Pagination<ArticleResponse> paginationResponse = new Pagination<>(
                List.of(articleResponse),
                1,
                10,
                1L,
                1,
                true
        );

        when(articleHandler.getArticles(
                "asc",
                "articleName",
                null,
                null,
                1,
                10)
        ).thenReturn(paginationResponse);

        mockMvc.perform(get("/article/")
                        .param("sortOrder", "asc")
                        .param("filterBy", "articleName")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items[0].articleId").value(1))
                .andExpect(jsonPath("$.items[0].articleName").value("IPhone 13"))
                .andExpect(jsonPath("$.items[0].articleDescription").
                        value("Apple smartphone with advanced features"))
                .andExpect(jsonPath("$.items[0].articleAmount").value("100"))
                .andExpect(jsonPath("$.items[0].articlePrice").value("999.99"))
                .andExpect(jsonPath("$.items[0].articleBrand.brandName").value("Apple"))
                .andExpect(jsonPath("$.items[0].articleCategories[0].name").value("PHONES"))
                .andExpect(jsonPath("$.pageNo").value(1))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalItems").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.isLastPage").value(true));
    }

    @Test
    void givenAscSortOrder_whenGetArticles_thenReturnsArticlesInAscendingOrder() throws Exception {

        ArticleResponse article1 = new ArticleResponse();
        article1.setArticleName("Adidas Flask");
        ArticleResponse article2 = new ArticleResponse();
        article2.setArticleName("ZiplocX");

        Pagination<ArticleResponse> paginationResponse = new Pagination<>(
                List.of(article1, article2),
                1,
                10,
                2L,
                1,
                true
        );

        when(articleHandler.getArticles(
                "asc",
                "articleName",
                null,
                null,
                1,
                10)
        ).thenReturn(paginationResponse);

        mockMvc.perform(get("/article/")
                        .param("sortOrder", "asc")
                        .param("filterBy", "articleName")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].articleName").value("Adidas Flask"))
                .andExpect(jsonPath("$.items[1].articleName").value("ZiplocX"));
    }

    @Test
    void givenBadPaginationRequest_whenGetArticles_PageOutOfBounds_thenReturnsBadRequest() throws Exception {

        Integer requestedPage = 10;
        Integer totalPages = 5;

        when(articleHandler.getArticles(
                "asc",
                "articleName",
                null,
                null,
                requestedPage, 10)
        ).thenThrow(new PageOutOfBoundsException(requestedPage, totalPages));

        mockMvc.perform(get("/article/")
                        .param("sortOrder", "asc")
                        .param("filterBy", "articleName")
                        .param("page", String.valueOf(requestedPage))
                        .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response.statusCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.response.message").value("Page 10 is out of range. Total pages: 5"))
                .andExpect(jsonPath("$.pageNo").value(requestedPage))
                .andExpect(jsonPath("$.totalPages").value(totalPages));
    }

    @Test
    void givenInvalidFilteringParameter_whenGetArticles_thenInvalidFilteringReturnsBadRequest() throws Exception {


        when(articleHandler.getArticles(
                "asc",
                "art",
                null,
                null,
                1,
                10)
        ).thenThrow(new InvalidFilteringParameterException("art"));

        mockMvc.perform(get("/article/")
                        .param("sortOrder", "asc")
                        .param("filterBy", "art")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message")
                        .value("Invalid value for filterBy parameter: art. Allowed values are 'articleName', 'brandName', 'categoryName'."));
    }

    @Test
    void givenValidSupplyRequest_whenUpdateArticleAmount_thenReturns200() throws Exception {
        SupplyRequest supplyRequest = new SupplyRequest();
        supplyRequest.setSupplyArticleIds(List.of(1, 2));
        supplyRequest.setSupplyArticleAmounts(List.of(50, 100));

        when(articleHandler.updateArticleSupply(any(SupplyRequest.class))).thenReturn(true);

        mockMvc.perform(patch("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    void givenInvalidSupplyRequest_whenUpdateArticleAmount_thenReturns400() throws Exception {
        SupplyRequest invalidRequest = new SupplyRequest();
        invalidRequest.setSupplyArticleIds(List.of(-1, -2));
        invalidRequest.setSupplyArticleAmounts(List.of(50, 100));

        mockMvc.perform(patch("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenNonExistingArticle_whenUpdateArticleAmount_thenReturns404() throws Exception {

        SupplyRequest supplyRequest = new SupplyRequest();
        supplyRequest.setSupplyArticleIds(List.of(4, 5));
        supplyRequest.setSupplyArticleAmounts(List.of(50, 100));

        when(articleHandler.updateArticleSupply(any(SupplyRequest.class)))
                .thenThrow(new ArticleNotFoundException(List.of(4, 5)));

        mockMvc.perform(patch("/article/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplyRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenValidArticleIds_whenGetArticlesByIds_thenReturnsArticlesList() throws Exception {
        List<Integer> articleIdList = List.of(1, 2);
        List<ArticleResponse> articleResponseList = List.of(articleResponse);

        when(articleHandler.getArticlesByIds(articleIdList)).thenReturn(articleResponseList);

        mockMvc.perform(get("/article/get-articles-by-ids")
                        .param("articleIdList", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(articleResponseList.size()))
                .andExpect(jsonPath("$[0].articleId").value(articleResponse.getArticleId()))
                .andExpect(jsonPath("$[0].articleName").value(articleResponse.getArticleName()));
    }

    @Test
    void givenInvalidArticleIds_whenGetArticlesByIds_thenReturnsEmptyList() throws Exception {
        List<Integer> articleIdList = List.of(999, 1000);
        List<ArticleResponse> emptyResponseList = Collections.emptyList();

        when(articleHandler.getArticlesByIds(articleIdList)).thenReturn(emptyResponseList);

        mockMvc.perform(get("/article/get-articles-by-ids")
                        .param("articleIdList", "999", "1000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));
    }

    @Test
    void givenInvalidArticleIds_whenGetArticlesByIds_thenReturns404() throws Exception {
        List<Integer> invalidArticleIdList = List.of(-1, -2);

        when(articleHandler.getArticlesByIds(invalidArticleIdList))
                .thenThrow(new ArticleNotFoundException(invalidArticleIdList));

        mockMvc.perform(get("/article/get-articles-by-ids")
                        .param("articleIdList", "-1", "-2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.NOT_FOUND.name()));
    }

}
