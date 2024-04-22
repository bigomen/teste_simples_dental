package com.simplesDental.teste.repository.profissional;

import com.simplesDental.teste.model.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, UUID>, ProfissionalRepositoryCustom {

    Boolean existsByIdAndStatus(UUID id, Boolean status);

    Optional<Profissional> findByIdAndStatus(UUID id, Boolean status);
}
