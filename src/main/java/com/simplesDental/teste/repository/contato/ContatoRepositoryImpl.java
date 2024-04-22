package com.simplesDental.teste.repository.contato;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.simplesDental.teste.exception.BadUserException;
import com.simplesDental.teste.model.Contato;
import com.simplesDental.teste.model.QContato;
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
public class ContatoRepositoryImpl extends QuerydslRepositorySupport implements ContatoRepositoryCustom {

    private final EntityManager entityManager;

    public ContatoRepositoryImpl(EntityManager entityManager){
        super(Contato.class);
        this.entityManager = entityManager;
    }

    public List<Contato> listAll(RestFilterParams params){

        QContato qContato = QContato.contato1;
        QProfissional qProfissional = qContato.profissional;
        JPAQuery<Contato> query = new JPAQuery<>(entityManager);

        if(Objects.nonNull(params.getFields()) && !params.getFields().isEmpty()){

            if(params.getFields().stream().distinct().count() != params.getFields().size()){
                throw new BadUserException("invalid.field.repeat");
            }

            params.getFields().replaceAll(field -> CaseUtils.toCamelCase(field, false, '_'));

            Expression<?>[] expressions = params.getFields().stream().map(attribute -> switch (attribute.trim()) {
                case "id" -> qContato.id;
                case "nome" -> qContato.nome;
                case "contato" -> qContato.contato;
                case "createdDate" -> qContato.createdDate;
                case "profissionalId" -> qContato.profissionalId;
                case "profissional" -> null;
                default -> throw new BadUserException("invalid.field");
            }).filter(Objects::nonNull).toArray(Expression<?>[]::new);

            if(expressions.length == 0){
                throw new BadUserException("invalid.field");
            }

            query.select(expressions)
                    .from(qContato)
                    .join(qProfissional);

        }else {
            query.select(qContato)
                    .from(qContato)
                    .join(qProfissional);
        }

        if(Objects.nonNull(params.getQ()) && !params.getQ().isBlank()){

            String param = "%" + params.getQ() + "%";
            BooleanExpression predicate = qContato.isNotNull()
                    .and(qProfissional.status.eq(true))
                    .and(qContato.nome.likeIgnoreCase(param).or(qContato.contato.likeIgnoreCase(param)));

            query.where(predicate);

        }else {
            BooleanExpression predicate = qContato.isNotNull()
                    .and(qProfissional.status.eq(true));

            query.where(predicate);
        }

        query.orderBy(qContato.nome.asc());

        if(Objects.nonNull(params.getFields()) && !params.getFields().isEmpty()){
            List<?> results = query.fetch();
            return tupleToEntity(results, params.getFields());
        }

        return query.fetch();
    }

    private List<Contato> tupleToEntity(List<?> tuples, List<String> fields){
        List<Contato> contatoList = new ArrayList<>();
        tuples.forEach(tuple -> {
            String tupleResult = tuple.toString();
            tupleResult = tupleResult.replace("[", "");
            tupleResult = tupleResult.replace("]", "");

            String[] values = tupleResult.split(",");
            Arrays.asList(values).replaceAll(String::trim);

            Contato contato = new Contato();

            int index = 0;
            for(String value : values){
                String fieldName = fields.get(index);

                try {
                    Field field = contato.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);

                    if (field.getType().equals(String.class)) {
                        field.set(contato, value);
                    }else if(field.getType().equals(UUID.class)){
                        field.set(contato, UUID.fromString(value));
                    }else if(field.getType().equals(LocalDate.class)){
                        field.set(contato, LocalDate.parse(value));
                    }else if(field.getType().equals(ZonedDateTime.class)){
                        field.set(contato, ZonedDateTime.parse(value));
                    }else if(field.getType().equals(Long.class)){
                        field.set(contato, Long.parseLong(value));
                    }else if(field.getType().equals(Integer.class)){
                        field.set(contato, Integer.parseInt(value));
                    }

                    index++;
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            contatoList.add(contato);
        });
        return contatoList;
    }
}
