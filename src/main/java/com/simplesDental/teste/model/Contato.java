package com.simplesDental.teste.model;

import com.simplesDental.teste.mapper.ContatoMapper;
import com.simplesDental.teste.rest.RestContato;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "CONTATOS")
@Getter
@Setter
@NoArgsConstructor
public class Contato implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "CONTATO")
    private String contato;

    @Column(name = "CREATED_DATE", updatable = false)
    private ZonedDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROFISSIONAL_ID", referencedColumnName = "ID", updatable = false, insertable = false)
    private Profissional profissional;

    @Column(name = "PROFISSIONAL_ID")
    private UUID profissionalId;

    @PrePersist
    private void prePersist(){
        this.createdDate = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contato contato1 = (Contato) o;
        return Objects.equals(id, contato1.id) && Objects.equals(nome, contato1.nome) && Objects.equals(contato, contato1.contato) && Objects.equals(createdDate, contato1.createdDate) && Objects.equals(profissionalId, contato1.profissionalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, contato, createdDate, profissionalId);
    }

    @Override
    public String toString() {
        return "Contato{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", contato='" + contato + '\'' +
                ", createdDate=" + createdDate +
                ", profissionalId=" + profissionalId +
                '}';
    }

    public RestContato toRest(){
        return ContatoMapper.INSTANCE.modelToRest(this);
    }
}
