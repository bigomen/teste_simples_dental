package com.simplesDental.teste.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Builder
public class ErrorMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Returns the message that should be printed to the user", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("userMessage")
    private String userMessage;

    @Schema(description = "Returns the API technical error", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("apiMessage")
    private String apiMessage;

    @Schema(description = "Reports the date the error occurred", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("time")
    private ZonedDateTime time;

    @Schema(description = "Returns the http code for the type of error that occurred", accessMode = Schema.AccessMode.READ_ONLY, allowableValues = {"200","201","202","400","404","500"})
    @JsonProperty("statusCode")
    private Integer statusCode;
}
