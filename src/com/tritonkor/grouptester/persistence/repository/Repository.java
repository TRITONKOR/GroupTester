package com.tritonkor.grouptester.persistence.repository;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface Repository <E extends Entity> {

    E findById(UUID id);

    List<E> findAll();

    List<E> findAll(Predicate<E> filter);

    E add(E entity);
    E remove(E entity);
    E remove(UUID id);
}
