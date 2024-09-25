package com.pragma.emazon_stock.infrastructure.feing;

import com.pragma.emazon_stock.domain.exceptions.TransactionServiceUnavailableException;
import com.pragma.emazon_stock.domain.exceptions.UnauthorizedException;
import com.pragma.emazon_stock.domain.utils.Constants;
import feign.codec.ErrorDecoder;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, feign.Response response) {
        HttpStatus status = HttpStatus.valueOf(response.status());

        return switch (status) {
            case BAD_REQUEST -> new BadRequestException(Constants.INVALID_REQUEST_ERROR + methodKey);
            case UNAUTHORIZED -> new UnauthorizedException(Constants.UNAUTHORIZED_ERROR + methodKey);
            case SERVICE_UNAVAILABLE, GATEWAY_TIMEOUT ->
                    new TransactionServiceUnavailableException(Constants.TRANSACTION_SERVICE_UNAVAILABLE + methodKey);
            case INTERNAL_SERVER_ERROR ->
                    new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Constants.INTERNAL_SERVER_ERROR);
            default -> defaultErrorDecoder.decode(methodKey, response);
        };
    }
}