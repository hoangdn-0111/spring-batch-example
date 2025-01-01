/* gkc_hash_code : 01GJEZS9QG3HA09TW4KD3D1JXB */
package org.example.springbatch.dto.error;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ErrorResponse implements Serializable {
    private Integer errorCode;
    private String errorMessage;
    private Object errorObject;
    private Object debug;

    public ErrorResponse(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
