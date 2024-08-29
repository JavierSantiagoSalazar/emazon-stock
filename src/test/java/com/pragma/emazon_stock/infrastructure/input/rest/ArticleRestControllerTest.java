package com.pragma.emazon_stock.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.emazon_stock.application.dto.article.ArticleRequest;
import com.pragma.emazon_stock.application.handler.article.ArticleHandler;
import com.pragma.emazon_stock.domain.exceptions.ArticleAlreadyExistsException;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
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

    @Autowired
    private ObjectMapper objectMapper;

    private ArticleRequest articleRequest;

    @BeforeEach
    public void setUp() {

        articleRequest = new ArticleRequest();
        articleRequest.setArticleName("iPhone 13");
        articleRequest.setArticleDescription("Apple smartphone with advanced features");
        articleRequest.setArticleAmount(100);
        articleRequest.setArticlePrice(999.99);
        articleRequest.setArticleCategories(Arrays.asList("Technology", "Mobile"));
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

}
