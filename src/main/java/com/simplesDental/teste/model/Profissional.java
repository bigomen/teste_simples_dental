package com.simplesDental.teste.model;

import com.simplesDental.teste.mapper.ProfissionalMapper;
import com.simplesDental.teste.rest.RestProfissional;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "PROFISSIONAIS")
@Getter
@Setter
@NoArgsConstructor
public class Profissional implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "CARGO")
    private String cargo;

    @Column(name = "NASCIMENTO")
    private LocalDate nascimento;

    @Column(name = "CREATED_DATE", updatable = false)
    private ZonedDateTime createdDate;

    @OneToMany(mappedBy = "profissional", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contato> contatoList;

    @Column(name = "STATUS")
    private Boolean status;

    @PrePersist
    private void prePersist(){
        if(!this.contatoList.isEmpty()){
            this.contatoList.forEach(c -> c.setProfissionalId(this.id));
        }
        this.createdDate = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
        this.status = true;
    }

    @PreUpdate
    private void preUpdate(){
        if(!this.contatoList.isEmpty()){
            this.contatoList.forEach(c -> c.setProfissionalId(this.id));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profissional that = (Profissional) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(cargo, that.cargo) && Objects.equals(nascimento, that.nascimento) && Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cargo, nascimento, createdDate);
    }

    @Override
    public String toString() {
        return "Profissional{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cargo='" + cargo + '\'' +
                ", nascimento=" + nascimento +
                ", createdDate=" + createdDate +
                ", contatoList=" + contatoList +
                '}';
    }

    public RestProfissional toRest(){
        return ProfissionalMapper.INSTANCE.modelToRest(this);
    }
}
