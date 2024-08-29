package com.pragma.emazon_stock.infrastructure.configuration.exception.exceptionhandler;

import com.pragma.emazon_stock.domain.exceptions.*;
import com.pragma.emazon_stock.infrastructure.configuration.exception.dto.PaginatedResponse;
import com.pragma.emazon_stock.infrastructure.configuration.exception.dto.Response;
import com.pragma.emazon_stock.infrastructure.utils.Constants;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<Response> handleCategoryAlreadyExistsException(
            CategoryAlreadyExistsException categoryAlreadyExistsException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.CONFLICT)
                        .message(Constants.CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE)
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(CategoryDoesNotExistException.class)
    public ResponseEntity<Response> handleCategoryDoesNotExistException(
            CategoryDoesNotExistException categoryDoesNotExistException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.UNPROCESSABLE_ENTITY)
                        .message(categoryDoesNotExistException.getMessage())
                        .build(),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<Response> handleBrandAlreadyExistsException(
            BrandAlreadyExistsException brandAlreadyExistsException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.CONFLICT)
                        .message(Constants.BRAND_ALREADY_EXISTS_EXCEPTION_MESSAGE)
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(BrandDoesNotExistException.class)
    public ResponseEntity<Response> handleBrandDoesNotExistException(
            BrandDoesNotExistException brandDoesNotExistException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.UNPROCESSABLE_ENTITY)
                        .message(brandDoesNotExistException.getMessage())
                        .build(),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(ArticleAlreadyExistsException.class)
    public ResponseEntity<Response> handleArticleAlreadyExistsException(
            ArticleAlreadyExistsException articleAlreadyExistsException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.CONFLICT)
                        .message(Constants.ARTICLE_ALREADY_EXISTS_EXCEPTION_MESSAGE)
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(NotUniqueArticleCategoriesException.class)
    public ResponseEntity<Response> handleNotUniqueArticleCategoriesException(
            NotUniqueArticleCategoriesException notUniqueArticleCategoriesException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .message(Constants.ARTICLE_CATEGORIES_NOT_UNIQUE_MESSAGE)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ArticleCategoryOutOfBoundsException.class)
    public ResponseEntity<Response> handleArticleCategoryOutOfBoundsException(
            ArticleCategoryOutOfBoundsException articleCategoryOutOfBoundsException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .message(Constants.ARTICLE_CATEGORIES_OUT_OF_BOUNDS_MESSAGE)
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException
    ) {

        List<String> errors = methodArgumentNotValidException.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();


        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .message(errors.toString())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NoContentCategoryException.class)
    public ResponseEntity<Response> handleNoContentCategoryException(
            NoContentCategoryException noContentCategoryException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.NO_CONTENT)
                        .message(Constants.CATEGORY_NO_CONTENT_MESSAGE)
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }

    @ExceptionHandler(NoContentBrandException.class)
    public ResponseEntity<Response> handleNoContentBrandException(
            NoContentBrandException noContentBrandException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.NO_CONTENT)
                        .message(Constants.BRAND_NO_CONTENT_MESSAGE)
                        .build(),
                HttpStatus.NO_CONTENT
        );
    }

    @ExceptionHandler(PageOutOfBoundsException.class)
    public ResponseEntity<PaginatedResponse> handlePageOutOfBoundsException(
            PageOutOfBoundsException pageOutOfBoundsException
    ) {

        return new ResponseEntity<>(
                new PaginatedResponse(
                        Response.builder()
                                .statusCode(HttpStatus.BAD_REQUEST)
                                .message(pageOutOfBoundsException.getMessage())
                                .build(),
                        pageOutOfBoundsException.getRequestedPage(),
                        pageOutOfBoundsException.getTotalPages()
                ),
                HttpStatus.BAD_REQUEST
        );
    }

}
