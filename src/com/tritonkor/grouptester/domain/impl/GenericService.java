package com.tritonkor.grouptester.domain.impl;

import com.tritonkor.grouptester.domain.Service;
import com.tritonkor.grouptester.domain.exception.EntityNotFoundException;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.repository.Repository;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * The GenericService class is a generic implementation of the Service interface, providing common
 * methods for working with entities.
 *
 * @param <E> The type of entity handled by the service.
 */
public class GenericService<E extends Entity> implements Service<E> {

    /**
     * The repository responsible for data access and storage.
     */
    private final Repository<E> repository;

    /**
     * Constructs a GenericService with the specified repository.
     *
     * @param repository The repository to be used for data access.
     */
    public GenericService(Repository<E> repository) {
        this.repository = repository;
    }

    /**
     * Retrieves an entity by its unique identifier (UUID).
     *
     * @param id The unique identifier of the entity.
     * @return The entity with the specified ID.
     * @throws EntityNotFoundException if no entity is found for the given ID.
     */
    @Override
    public E get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nothing found for this id"));
    }

    /**
     * Retrieves all entities of the specified type.
     *
     * @return A set containing all entities of the specified type.
     */
    @Override
    public Set<E> getAll() {
        return repository.findAll();
    }

    /**
     * Retrieves entities based on the provided filter predicate.
     *
     * @param filter The predicate used to filter entities.
     * @return A set containing entities that satisfy the filter predicate.
     */
    @Override
    public Set<E> getAll(Predicate<E> filter) {
        return repository.findAll(filter);
    }

    /**
     * Adds a new entity to the service.
     *
     * @param entity The entity to be added.
     * @return The added entity.
     */
    @Override
    public E add(E entity) {
        return repository.add(entity);
    }

    /**
     * Removes the specified entity from the service.
     *
     * @param entity The entity to be removed.
     * @return true if the removal is successful, false otherwise.
     */
    @Override
    public boolean remove(E entity) {
        return repository.remove(entity);
    }
}
