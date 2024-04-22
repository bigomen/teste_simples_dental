package com.simplesDental.teste.repository.contato;

import com.simplesDental.teste.model.Contato;
import com.simplesDental.teste.rest.RestFilterParams;

import java.util.List;

public interface ContatoRepositoryCustom {

    List<Contato> listAll(RestFilterParams params);
}
