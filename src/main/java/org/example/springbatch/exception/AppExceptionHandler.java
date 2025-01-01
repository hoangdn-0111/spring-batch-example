/* gkc_hash_code : 01GJEZS9QG3HA09TW4KD3D1JXB */
package org.example.springbatch.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.springbatch.dto.error.ErrorResponse;
import org.example.springbatch.exception.type.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(AppException.class)
    public final ResponseEntity<ErrorResponse> handleRtmException(AppException e) {
        log.error(e.getMessage(), e);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (e instanceof DataNotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof DataConflictException) {
            status = HttpStatus.CONFLICT;
        } else if (e instanceof BadRequestException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof UnauthorizedException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (e instanceof ForbiddenException) {
            status = HttpStatus.FORBIDDEN;
        }

        ErrorResponse errorResponse = e.getErrorResponse();
        setLogDebug(errorResponse, e);

        return new ResponseEntity<>(errorResponse, status);
    }

    private void setLogDebug(ErrorResponse errorResponse, Exception exception) {
        if (!(exception instanceof AppException)) {
            log.error(exception.getMessage(), exception);
        }
        // only development env
        if (log.isDebugEnabled()) {
            errorResponse.setDebug(exception.getStackTrace());
        }
    }

    @ExceptionHandler(CsvInvalidException.class)
    public final ResponseEntity<?> handleSgUploadException(CsvInvalidException ex, WebRequest request) {
        log.debug(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
