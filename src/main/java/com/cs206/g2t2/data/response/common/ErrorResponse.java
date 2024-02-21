package com.cs206.g2t2.data.response.common;

import com.cs206.g2t2.data.response.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse implements Response {
    private String error;
    private String message;
}
