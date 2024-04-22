package com.simplesDental.teste.mapper;

import com.simplesDental.teste.model.Contato;
import com.simplesDental.teste.model.Profissional;
import com.simplesDental.teste.rest.RestProfissional;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = ContatoMapper.class)
public interface ProfissionalMapper extends LazyLoadingAwareMapper{

    ProfissionalMapper INSTANCE = Mappers.getMapper(ProfissionalMapper.class);

    RestProfissional modelToRest(Profissional profissional);

    @Mappings({
            @Mapping(target = "status", ignore = true)
    })
    Profissional restToModel(RestProfissional restProfissional);

    @Mappings({
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "status", ignore = true)
    })
    void copy(RestProfissional source, @MappingTarget Profissional target);

    @Condition
    default boolean isNotLazyLoadedRole(List<Contato> contatoList){
        return isNotLazyLoaded(contatoList);
    }

}
