package org.example.springbatch.exception.type;

import org.example.springbatch.dto.error.ErrorResponse;
import org.example.springbatch.exception.AppException;

public class ForbiddenException extends AppException {

    public ForbiddenException() {
        this(new ErrorResponse());
    }

    public ForbiddenException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
