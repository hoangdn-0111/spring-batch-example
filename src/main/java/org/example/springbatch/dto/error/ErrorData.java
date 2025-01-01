/* gkc_hash_code : 01GJEZS9QG3HA09TW4KD3D1JXB */
package org.example.springbatch.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorData {
    private int code;
    private String message;
}
