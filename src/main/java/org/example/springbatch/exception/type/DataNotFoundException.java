package org.example.springbatch.exception.type;

import org.example.springbatch.dto.error.ErrorResponse;
import org.example.springbatch.exception.AppException;

public class DataNotFoundException extends AppException {

    public DataNotFoundException() {
        this(new ErrorResponse());
    }

    public DataNotFoundException(ErrorResponse errorResponse) {
        super(errorResponse);
    }
}
