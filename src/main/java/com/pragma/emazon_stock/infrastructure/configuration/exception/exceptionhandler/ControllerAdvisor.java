package com.pragma.emazon_stock.infrastructure.configuration.exception.exceptionhandler;

import com.pragma.emazon_stock.domain.exceptions.ArticleAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.ArticleCategoryOutOfBoundsException;
import com.pragma.emazon_stock.domain.exceptions.ArticleNotFoundException;
import com.pragma.emazon_stock.domain.exceptions.BrandAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.BrandDoesNotExistException;
import com.pragma.emazon_stock.domain.exceptions.CategoryAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.CategoryDoesNotExistException;
import com.pragma.emazon_stock.domain.exceptions.InvalidFilteringParameterException;
import com.pragma.emazon_stock.domain.exceptions.JwtIsEmptyException;
import com.pragma.emazon_stock.domain.exceptions.NoContentArticleException;
import com.pragma.emazon_stock.domain.exceptions.NoContentBrandException;
import com.pragma.emazon_stock.domain.exceptions.NoContentCategoryException;
import com.pragma.emazon_stock.domain.exceptions.NotUniqueArticleCategoriesException;
import com.pragma.emazon_stock.domain.exceptions.PageOutOfBoundsException;
import com.pragma.emazon_stock.domain.exceptions.SupplyAmountMismatchException;
import com.pragma.emazon_stock.domain.exceptions.TransactionServiceUnavailableException;
import com.pragma.emazon_stock.domain.exceptions.UnauthorizedException;
import com.pragma.emazon_stock.domain.utils.Constants;
import com.pragma.emazon_stock.infrastructure.configuration.exception.dto.PaginatedResponse;
import com.pragma.emazon_stock.infrastructure.configuration.exception.dto.Response;
import feign.RetryableException;
import org.apache.coyote.BadRequestException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
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

    @ExceptionHandler(InvalidFilteringParameterException.class)
    public ResponseEntity<Response> handleInvalidSortingParameterException(
            InvalidFilteringParameterException invalidFilteringParameterException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .message(invalidFilteringParameterException.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
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

    @ExceptionHandler(SupplyAmountMismatchException.class)
    public ResponseEntity<Response> handleSupplyAmountMismatchException(
            SupplyAmountMismatchException supplyAmountMismatchException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .message(supplyAmountMismatchException.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ArticleNotFoundException.class)
    public ResponseEntity<Response> handleArticleNotFoundException(
            ArticleNotFoundException articleNotFoundException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.NOT_FOUND)
                        .message(articleNotFoundException.getMessage() + articleNotFoundException.getNotFoundIds())
                        .build(),
                HttpStatus.NOT_FOUND
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

    @ExceptionHandler(NoContentArticleException.class)
    public ResponseEntity<Response> handleNoContentArticleException(
            NoContentArticleException noContentArticleException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.NO_CONTENT)
                        .message(Constants.ARTICLE_NO_CONTENT_MESSAGE)
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

    @ExceptionHandler(JwtIsEmptyException.class)
    public ResponseEntity<Response> handleJwtIsEmptyException(
            JwtIsEmptyException jwtIsEmptyException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.UNAUTHORIZED)
                        .message(Constants.JWT_IS_EMPTY_ERROR)
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Response> handleUnauthorizedException(
            UnauthorizedException unauthorizedException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.UNAUTHORIZED)
                        .message(unauthorizedException.getMessage())
                        .build(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<Response> handleConnectException(
            ConnectException connectException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.SERVICE_UNAVAILABLE)
                        .message(connectException.getMessage())
                        .build(),
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<Response> handleRetryableException(
            RetryableException retryableException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.SERVICE_UNAVAILABLE)
                        .message(retryableException.getMessage())
                        .build(),
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    @ExceptionHandler(TransactionServiceUnavailableException.class)
    public ResponseEntity<Response> handleTransactionServiceUnavailableException(
            TransactionServiceUnavailableException transactionServiceUnavailableException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.SERVICE_UNAVAILABLE)
                        .message(transactionServiceUnavailableException.getMessage())
                        .build(),
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Response> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException httpMessageNotReadableException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .message(httpMessageNotReadableException.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Response> handleBadRequestException(
            BadRequestException badRequestException
    ) {
        return new ResponseEntity<>(
                Response.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .message(badRequestException.getMessage())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

}
