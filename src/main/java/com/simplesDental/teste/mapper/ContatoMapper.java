package com.simplesDental.teste.mapper;

import com.simplesDental.teste.model.Contato;
import com.simplesDental.teste.model.Profissional;
import com.simplesDental.teste.rest.RestContato;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContatoMapper extends LazyLoadingAwareMapper{

    ContatoMapper INSTANCE = Mappers.getMapper(ContatoMapper.class);

    RestContato modelToRest(Contato contato);

    @Mappings({
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "profissional", ignore = true)
    })
    Contato restToModel(RestContato restContato);

    @Mappings({
            @Mapping(target = "createdDate", ignore = true),
            @Mapping(target = "profissional", ignore = true)
    })
    void copy(RestContato source, @MappingTarget Contato target);

    @Condition
    default boolean isNotLazyLoadedRole(Profissional profissional){
        return isNotLazyLoaded(profissional);
    }
}
