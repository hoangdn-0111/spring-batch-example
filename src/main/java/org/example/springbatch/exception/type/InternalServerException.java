package org.example.springbatch.exception.type;

import org.example.springbatch.dto.error.ErrorResponse;
import org.example.springbatch.exception.AppException;

public class InternalServerException extends AppException {

    public InternalServerException() {
        this(new ErrorResponse());
    }

    public InternalServerException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
