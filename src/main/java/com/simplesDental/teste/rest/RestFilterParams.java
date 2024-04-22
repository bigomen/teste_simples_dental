package com.simplesDental.teste.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RestFilterParams implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Filter to search that contain the text in any of its attributes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String q;

    @Schema(description = "When present, only the fields listed in fields should be returned", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<String> fields;
}
