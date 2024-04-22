package com.simplesDental.teste.repository.contato;

import com.simplesDental.teste.model.Contato;
import com.simplesDental.teste.model.Profissional;
import com.simplesDental.teste.rest.RestContato;
import com.simplesDental.teste.rest.RestProfissional;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ContatoRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ContatoRepository contatoRepository;

    @Test
    @DisplayName("Should return all contacts from Profissional")
    void findAllByProfissionalId() {

        RestContato restContato1 = new RestContato();
        RestContato restContato2 = new RestContato();

        restContato1.setNome("Telefone");
        restContato1.setContato("8739102619");
        restContato2.setNome("Celular");
        restContato2.setContato("87993970669");

        List<RestContato> restContatoList = new ArrayList<>(List.of(restContato1, restContato2));

        RestProfissional rest = new RestProfissional();
        rest.setNome("Carlos César dos Santos");
        rest.setCargo("Desenvolvedor");
        rest.setNascimento(LocalDate.of(1980,3,4));
        rest.setContatoList(restContatoList);

        Profissional model = this.createProfissional(rest);

        assertTrue(Objects.nonNull(model.getId()));

        List<Contato> contatoList = this.contatoRepository.findAllByProfissionalId(model.getId());
        assertTrue(Objects.nonNull(contatoList) && !contatoList.isEmpty());
        assertEquals(contatoList.size(), restContatoList.size());
    }

    private Profissional createProfissional(RestProfissional rest){
        Profissional model = rest.toModel();

        this.entityManager.persist(model);
        return model;
    }
}