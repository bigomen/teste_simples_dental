package com.simplesDental.teste.repository.profissional;

import com.simplesDental.teste.model.Profissional;
import com.simplesDental.teste.rest.RestFilterParams;

import java.util.List;

public interface ProfissionalRepositoryCustom {

    List<Profissional> listAll(RestFilterParams params);
}
