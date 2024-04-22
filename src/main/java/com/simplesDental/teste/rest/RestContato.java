package com.simplesDental.teste.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplesDental.teste.mapper.ContatoMapper;
import com.simplesDental.teste.model.Contato;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RestContato implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique identifier of the contato, required to update")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Contact name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "field.name.contact")
    @JsonProperty("nome")
    private String nome;

    @Schema(description = "Contact", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "field.contact")
    @JsonProperty("contato")
    private String contato;

    @Schema(description = "Contact record creation date", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("created_date")
    private ZonedDateTime createdDate;

    @Schema(description = "Professional who has this contact", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("profissional")
    private RestProfissional profissional;

    @Schema(description = "Unique identifier of the professional who has this contact, necessary if using contact endpoints")
    @JsonProperty("profissional_id")
    private UUID profissionalId;

    public Contato toModel(){
        return ContatoMapper.INSTANCE.restToModel(this);
    }

    public void copyTo(Contato model){
        ContatoMapper.INSTANCE.copy(this, model);
    }
}
