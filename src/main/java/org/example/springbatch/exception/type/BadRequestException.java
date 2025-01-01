package org.example.springbatch.exception.type;

import org.example.springbatch.dto.error.ErrorResponse;
import org.example.springbatch.exception.AppException;

public class BadRequestException extends AppException {

    public BadRequestException() {
        this(new ErrorResponse());
    }

    public BadRequestException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
