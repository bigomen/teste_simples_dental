package com.simplesDental.teste.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.simplesDental.teste.mapper.ProfissionalMapper;
import com.simplesDental.teste.model.Profissional;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RestProfissional implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "Unique professional registration identifier", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Professional's name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "field.name")
    @JsonProperty("nome")
    private String nome;

    @Schema(description = "Professional position", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"Desenvolvedor","Designer","Suporte","Tester"})
    @NotBlank(message = "field.role")
    @JsonProperty("cargo")
    private String cargo;

    @Schema(description = "Professional's date of birth", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "field.birthday")
    @JsonProperty("nascimento")
    private LocalDate nascimento;

    @Schema(description = "Professional record creation date", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty("created_date")
    private ZonedDateTime createdDate;

    @Schema(description = "Professional contact list", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    @JsonProperty("contatos")
    private List<RestContato> contatoList;

    public Profissional toModel(){
        return ProfissionalMapper.INSTANCE.restToModel(this);
    }

    public void copyTo(Profissional model){
        ProfissionalMapper.INSTANCE.copy(this, model);
    }
}
