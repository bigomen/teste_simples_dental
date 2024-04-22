package com.simplesDental.teste.mapper;

import org.hibernate.Hibernate;

public interface LazyLoadingAwareMapper {
    default boolean isNotLazyLoaded(Object sourceList){
        return Hibernate.isInitialized(sourceList);
    }
}
