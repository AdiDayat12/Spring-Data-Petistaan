package com.abhishekvermaa10.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDTO {
    @Schema(description = "Provide date and time when error occurs")
    private LocalDateTime timeStamp;
    @Schema(description = "Provide error message")
    private String message;
    @Schema(description = "Provide the number of error status code")
    private int status;
    @Schema(description = "Provide enum of Http status")
    private HttpStatus error;
}
