package com.simplesDental.teste.repository.profissional;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.simplesDental.teste.exception.BadUserException;
import com.simplesDental.teste.model.Profissional;
import com.simplesDental.teste.model.QProfissional;
import com.simplesDental.teste.rest.RestFilterParams;
import jakarta.persistence.EntityManager;
import org.apache.commons.text.CaseUtils;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.*;

@Repository
public class ProfissionalRepositoryImpl extends QuerydslRepositorySupport implements ProfissionalRepositoryCustom {

    private final EntityManager entityManager;

    public ProfissionalRepositoryImpl(EntityManager entityManager){
        super(Profissional.class);
        this.entityManager = entityManager;
    }

    public List<Profissional> listAll(RestFilterParams params){

        QProfissional qProfissional = QProfissional.profissional;
        JPAQuery<Profissional> query = new JPAQuery<>(entityManager);

        if(Objects.nonNull(params.getFields()) && !params.getFields().isEmpty()){

            if(params.getFields().stream().distinct().count() != params.getFields().size()){
                throw new BadUserException("invalid.field.repeat");
            }
            params.getFields().replaceAll(field -> CaseUtils.toCamelCase(field, false, '_'));

            Expression<?>[] expressions = params.getFields().stream().map(attribute -> switch (attribute.trim()) {
                case "id" -> qProfissional.id;
                case "nome" -> qProfissional.nome;
                case "cargo" -> qProfissional.cargo;
                case "nascimento" -> qProfissional.nascimento;
                case "createdDate" -> qProfissional.createdDate;
                case "contatos" -> null;
                case "status" -> null;
                default -> throw new BadUserException("invalid.field");
            }).filter(Objects::nonNull).toArray(Expression<?>[]::new);

            if(expressions.length == 0){
                throw new BadUserException("invalid.field");
            }

            query.select(expressions).from(qProfissional);

        }else {
            query.select(qProfissional).from(qProfissional);
        }

        if(Objects.nonNull(params.getQ()) && !params.getQ().isBlank()){

            String param = "%" + params.getQ() + "%";
            BooleanExpression predicate = qProfissional.isNotNull()
                    .and(qProfissional.status.eq(true))
                    .and(qProfissional.nome.likeIgnoreCase(param).or(qProfissional.cargo.likeIgnoreCase(param)));

            query.where(predicate);

        }else{

            BooleanExpression predicate = qProfissional.isNotNull()
                    .and(qProfissional.status.eq(true));
            query.where(predicate);
        }

        query.orderBy(qProfissional.nome.asc());

        if(Objects.nonNull(params.getFields()) && !params.getFields().isEmpty()){
            List<?> results = query.fetch();
            return tupleToEntity(results, params.getFields());
        }
        return query.fetch();
    }

    private List<Profissional> tupleToEntity(List<?> tuples, List<String> fields){
        List<Profissional> profissionalList = new ArrayList<>();
        tuples.forEach(tuple -> {
            String tupleResult = tuple.toString();
            tupleResult = tupleResult.replace("[", "");
            tupleResult = tupleResult.replace("]", "");

            String[] values = tupleResult.split(",");
            Arrays.asList(values).replaceAll(String::trim);

            Profissional profissional = new Profissional();

            int index = 0;
            for(String value : values){
                String fieldName = fields.get(index);

                try {
                    Field field = profissional.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);

                    if (field.getType().equals(String.class)) {
                        field.set(profissional, value);
                    }else if(field.getType().equals(UUID.class)){
                        field.set(profissional, UUID.fromString(value));
                    }else if(field.getType().equals(LocalDate.class)){
                        field.set(profissional, LocalDate.parse(value));
                    }else if(field.getType().equals(ZonedDateTime.class)){
                        field.set(profissional, ZonedDateTime.parse(value));
                    }else if(field.getType().equals(Long.class)){
                        field.set(profissional, Long.parseLong(value));
                    }else if(field.getType().equals(Integer.class)){
                        field.set(profissional, Integer.parseInt(value));
                    }

                    index++;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            profissionalList.add(profissional);
        });
        return profissionalList;
    }
}
