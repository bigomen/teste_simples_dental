package com.simplesDental.teste.controller;

import com.simplesDental.teste.rest.RestFilterParams;
import com.simplesDental.teste.rest.RestProfissional;
import com.simplesDental.teste.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissionais")
@Validated
@Tag(name = "Professionals", description = "Endpoints for Professional CRUD")
public class ProfissionalController {

    @Autowired
    private ProfissionalService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Endpoint for registering a professional", description = "This endpoint returns a string confirming that the registration was successful")
    public String create(@RequestBody @Valid RestProfissional rest){
        return service.create(rest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Endpoint to view the list of registered professionals", description = "This endpoint will return a list of RestProfissional")
    public List<RestProfissional> list(RestFilterParams params){
        return service.list(params);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Endpoint to update a registered professional", description = "This endpoint will return a string confirming that the update was successful")
    public String update(@RequestBody @Valid RestProfissional rest, @PathVariable String id){
        return service.update(rest, id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Endpoint to view all data of a registered professional", description = "This endpoint will return all professional data as RestProfissional")
    public RestProfissional details(@PathVariable String id){
        return service.details(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Endpoint to delete a registered professional", description = "This endpoint will return a string confirming that the record has been successfully removed")
    public String delete(@PathVariable String id){
        return service.delete(id);
    }
}
