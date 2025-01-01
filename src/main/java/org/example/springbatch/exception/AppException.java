package org.example.springbatch.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.springbatch.dto.error.ErrorData;
import org.example.springbatch.dto.error.ErrorResponse;
import org.example.springbatch.exception.type.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends RuntimeException {
    private ErrorResponse errorResponse;

    public AppException() {
        this(new ErrorResponse());
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
        this.errorResponse = new ErrorResponse();
    }

    public AppException(ErrorResponse errorResponse) {
        super(errorResponse.getErrorMessage());
        this.errorResponse = errorResponse;
    }

    public AppException(String message) {
        super(message);
    }

    public static Builder serviceError() {
        return new Builder(AppException.class);
    }

    public static Builder dataNotFound() {
        return new Builder(DataNotFoundException.class);
    }

    public static Builder dataConflict() {
        return new Builder(DataConflictException.class);
    }

    public static Builder badRequest() {
        return new Builder(BadRequestException.class);
    }

    public static Builder unauthorized() {
        return new Builder(UnauthorizedException.class);
    }

    public static Builder forbidden() {
        return new Builder(ForbiddenException.class);
    }

    public static class Builder {
        private final Class<? extends AppException> exceptionClass;

        public Builder(Class<? extends AppException> exceptionClass) {
            this.exceptionClass = exceptionClass;
        }

        public AppException build(ErrorData errorData) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorMessage(errorData.getMessage());
            errorResponse.setErrorCode(errorData.getCode());
            return createCorrespondingException(errorResponse);
        }

        private AppException createCorrespondingException(ErrorResponse errorResponse) {
            if (exceptionClass == DataNotFoundException.class) {
                return new DataNotFoundException(errorResponse);
            }

            if (exceptionClass == AppException.class) {
                return new AppException(errorResponse);
            }

            if (exceptionClass == DataConflictException.class) {
                return new DataConflictException(errorResponse);
            }

            if (exceptionClass == BadRequestException.class) {
                return new BadRequestException(errorResponse);
            }

            if (exceptionClass == UnauthorizedException.class) {
                return new UnauthorizedException(errorResponse);
            }

            if (exceptionClass == ForbiddenException.class) {
                return new ForbiddenException(errorResponse);
            }

            throw new UnsupportedOperationException(exceptionClass.getSimpleName() + " is not supported");
        }
    }
}
