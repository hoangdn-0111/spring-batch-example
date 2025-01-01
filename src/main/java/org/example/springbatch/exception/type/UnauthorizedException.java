package org.example.springbatch.exception.type;

import org.example.springbatch.dto.error.ErrorResponse;
import org.example.springbatch.exception.AppException;

public class UnauthorizedException extends AppException {

    public UnauthorizedException() {
        this(new ErrorResponse());
    }

    public UnauthorizedException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
