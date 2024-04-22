package com.simplesDental.teste.service;

import com.simplesDental.teste.exception.BadUserException;
import com.simplesDental.teste.exception.NotFoundException;
import com.simplesDental.teste.model.Contato;
import com.simplesDental.teste.model.Profissional;
import com.simplesDental.teste.model.enums.CargoEnum;
import com.simplesDental.teste.repository.contato.ContatoRepository;
import com.simplesDental.teste.repository.profissional.ProfissionalRepository;
import com.simplesDental.teste.rest.RestContato;
import com.simplesDental.teste.rest.RestFilterParams;
import com.simplesDental.teste.rest.RestProfissional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProfissionalService extends BaseService{

    @Autowired
    private ProfissionalRepository repository;

    @Autowired
    private ContatoRepository contatoRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public String create(RestProfissional rest){

        if(Objects.nonNull(rest.getId())) throw new BadUserException("mustNull.id");

        validateRest(rest);

        Profissional model = rest.toModel();

        repository.save(model);

        this.log("create.profissional.succefully");
        this.log(model.toString());

        return this.sucessCreate("response.profissional.created", model.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String update(RestProfissional rest, String id){

        validateRest(rest);

        try {
            rest.setId(UUID.fromString(id));
        }catch (IllegalArgumentException ex){
            throw new BadUserException("invalid.id");
        }

        Profissional model = repository.findByIdAndStatus(rest.getId(), Boolean.TRUE)
                .orElseThrow(() -> new NotFoundException("entity.name.professional"));

        rest.copyTo(model);

        repository.save(model);

        this.log("update.profissional.succefully");
        this.log(model.toString());

        return this.getMessage("response.updated");
    }

    public List<RestProfissional> list(RestFilterParams params){
        List<Profissional> profissionalList = repository.listAll(params);
        if(profissionalList.isEmpty()) return new ArrayList<>();

        return profissionalList.stream().map(Profissional::toRest).toList();
    }

    public RestProfissional details(String id){
        UUID uuid;

        try {
            uuid = UUID.fromString(id);
        }catch (IllegalArgumentException ex){
            throw new BadUserException("invalid.id");
        }

        Profissional model = repository.findByIdAndStatus(uuid, Boolean.TRUE)
                .orElseThrow(() -> new NotFoundException("entity.name.professional"));

        List<Contato> contatoList = contatoRepository.findAllByProfissionalId(model.getId());
        if(!contatoList.isEmpty()){
            model.setContatoList(contatoList);
        }

        return model.toRest();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String delete(String id){
        UUID uuid;

        try {
            uuid = UUID.fromString(id);
        }catch (IllegalArgumentException ex){
            throw new BadUserException("invalid.id");
        }

        Profissional model = repository.findByIdAndStatus(uuid, Boolean.TRUE)
                .orElseThrow(() -> new NotFoundException("entity.name.professional"));

        model.setStatus(false);

        List<Contato> contatoList = contatoRepository.findAllByProfissionalId(model.getId());
        if(!contatoList.isEmpty()){
            model.getContatoList().clear();
            model.getContatoList().addAll(contatoList);
        }

        repository.save(model);

        this.log("delete.profissional.succefully");
        this.log(model.toString());

        String responseMessage = this.getMessage("response.deleted");
        return responseMessage.replace("{c}", this.getMessage("entity.name.professional"));
    }

    private void validateRest(RestProfissional rest){
        if(Objects.isNull(rest.getContatoList()) || rest.getContatoList().isEmpty()){
            throw new BadUserException("notNull.contatoList");
        }

        if(rest.getNascimento().isAfter(LocalDate.now())){
            throw new BadUserException("invalid.birthday");
        }

        if(!CargoEnum.getAll().contains(rest.getCargo())){
            throw new BadUserException("invalid.role");
        }

        if(rest.getContatoList().stream().map(RestContato::getId)
                .filter(Objects::nonNull)
                .distinct().count() != rest.getContatoList().stream().map(RestContato::getId)
                .filter(Objects::nonNull).count()){
            throw new BadUserException("invalid.id.repeat");
        }
    }
}