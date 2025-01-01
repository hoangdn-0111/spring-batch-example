package org.example.springbatch.exception.type;

import org.example.springbatch.dto.error.ErrorResponse;
import org.example.springbatch.exception.AppException;

public class DataConflictException extends AppException {

    public DataConflictException() {
        this(new ErrorResponse());
    }

    public DataConflictException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
