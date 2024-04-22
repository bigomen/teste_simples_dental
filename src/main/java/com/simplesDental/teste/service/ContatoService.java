package com.simplesDental.teste.service;

import com.simplesDental.teste.exception.BadUserException;
import com.simplesDental.teste.exception.NotFoundException;
import com.simplesDental.teste.model.Contato;
import com.simplesDental.teste.model.Profissional;
import com.simplesDental.teste.repository.contato.ContatoRepository;
import com.simplesDental.teste.repository.profissional.ProfissionalRepository;
import com.simplesDental.teste.rest.RestContato;
import com.simplesDental.teste.rest.RestFilterParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ContatoService extends BaseService{

    @Autowired
    private ContatoRepository repository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public String create(RestContato rest){

        if(Objects.nonNull(rest.getId())) throw new BadUserException("mustNull.id");

        Objects.requireNonNull(rest.getProfissionalId(), "inform.id");

        if(!profissionalRepository.existsByIdAndStatus(rest.getProfissionalId(), Boolean.TRUE)){
            throw new NotFoundException("entity.name.professional");
        }

        Contato model = rest.toModel();

        repository.save(model);

        this.log("create.contact.succefully");
        this.log(model.toString());

        return this.sucessCreate("response.contact.created", model.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String update(RestContato rest, String id){

        try {
            rest.setId(UUID.fromString(id));
        }catch (IllegalArgumentException ex){
            throw new BadUserException("invalid.id");
        }

        if(!profissionalRepository.existsByIdAndStatus(rest.getProfissionalId(), Boolean.TRUE)){
            throw new NotFoundException("entity.name.professional");
        }

        Contato model = repository.findById(rest.getId())
                .orElseThrow(() -> new NotFoundException("entity.name.contact"));

        rest.copyTo(model);

        repository.save(model);

        this.log("update.contact.succefully");
        this.log(model.toString());

        return this.getMessage("response.updated");
    }

    public List<RestContato> listAll(RestFilterParams params){
        List<Contato> contatoList = repository.listAll(params);
        if(contatoList.isEmpty()) return new ArrayList<>();

        return contatoList.stream().map(Contato::toRest).toList();
    }

    public RestContato details(String id){

        UUID uuid;

        try {
            uuid = UUID.fromString(id);
        }catch (IllegalArgumentException ex){
            throw new BadUserException("invalid.id");
        }

        Contato contato = repository.findByIdAndProfissionalStatus(uuid, Boolean.TRUE)
                .orElseThrow(() -> new NotFoundException("entity.name.contact"));

        RestContato rest = contato.toRest();

        Profissional profissional = profissionalRepository.findById(contato.getProfissionalId())
                .orElseThrow(() -> new NotFoundException("entity.name.professional"));

        rest.setProfissional(profissional.toRest());

        return rest;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String delete(String id){
        UUID uuid;

        try {
            uuid = UUID.fromString(id);
        }catch (IllegalArgumentException ex){
            throw new BadUserException("invalid.id");
        }

        Contato model = repository.findByIdAndProfissionalStatus(uuid, Boolean.TRUE)
                .orElseThrow(() -> new NotFoundException("entity.name.contact"));

        repository.delete(model);

        this.log("delete.contact.succefully");
        this.log(model.toString());

        String responseMessage = this.getMessage("response.deleted");
        return responseMessage.replace("{c}", this.getMessage("entity.name.contact"));
    }
}
