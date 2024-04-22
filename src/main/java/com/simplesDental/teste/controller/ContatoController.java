package com.simplesDental.teste.controller;

import com.simplesDental.teste.rest.RestContato;
import com.simplesDental.teste.rest.RestFilterParams;
import com.simplesDental.teste.service.ContatoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contatos")
@Tag(name = "Contacts", description = "Endpoints for Contact CRUD")
public class ContatoController {

    @Autowired
    private ContatoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Endpoint for registering a contact", description = "This endpoint returns a string confirming that the registration was successful")
    public String create(@RequestBody @Valid RestContato rest){
        return service.create(rest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Endpoint to update a registered contact", description = "This endpoint will return a string confirming that the update was successful")
    public String update(@RequestBody @Valid RestContato rest, @PathVariable String id){
        return service.update(rest, id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Endpoint to view the list of registered contacts", description = "This endpoint will return a list of RestContato")
    public List<RestContato> list(RestFilterParams params){
        return service.listAll(params);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Endpoint to view all data of a registered contact", description = "This endpoint will return all contact data as RestContato")
    public RestContato details(@PathVariable String id){
        return service.details(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Endpoint to delete a registered contact", description = "This endpoint will return a string confirming that the record has been successfully removed")
    public String delete(@PathVariable String id){
        return service.delete(id);
    }
}
