package com.simplesDental.teste.repository.contato;

import com.simplesDental.teste.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, UUID>, ContatoRepositoryCustom {

    List<Contato> findAllByProfissionalId(UUID profissionalId);

    @Query("SELECT c FROM Contato c JOIN c.profissional p WHERE c.id = :id AND p.status = :status")
    Optional<Contato> findByIdAndProfissionalStatus(UUID id, Boolean status);
}
