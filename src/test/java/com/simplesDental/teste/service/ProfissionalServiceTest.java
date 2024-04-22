package com.simplesDental.teste.service;

import com.simplesDental.teste.model.Profissional;
import com.simplesDental.teste.rest.RestContato;
import com.simplesDental.teste.rest.RestProfissional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class ProfissionalServiceTest {

    @Mock
    private ProfissionalService service;

    private RestProfissional restProfissional;
    private Profissional profissional;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        RestContato restContato1 = new RestContato();
        RestContato restContato2 = new RestContato();

        restContato1.setNome("Telefone");
        restContato1.setContato("8739102619");
        restContato2.setNome("Celular");
        restContato2.setContato("87993970669");

        List<RestContato> contatoList = new ArrayList<>(List.of(restContato1, restContato2));

        RestProfissional rest = new RestProfissional();
        rest.setNome("Carlos César dos Santos");
        rest.setCargo("Desenvolvedor");
        rest.setNascimento(LocalDate.of(1980,3,4));
        rest.setContatoList(contatoList);

        this.restProfissional = rest;
    }

    @Test
    @DisplayName("Should return 'works' if profissional create service is OK")
    void create(){

        doNothing().when(service).log(anyString());
        when(service.create(any())).thenReturn("Works");

        String result = service.create(this.restProfissional);
        assertThat(result).isEqualTo("Works");
    }

    @Test
    @DisplayName("Should return 'works' if professional update is OK")
    void update(){

        doNothing().when(service).log(anyString());
        when(service.update(any(), any())).thenReturn("Works");

        String result = service.update(this.restProfissional, UUID.randomUUID().toString());
        assertThat(result).isEqualTo("Works");
    }
}