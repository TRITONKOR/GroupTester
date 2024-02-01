package com.tritonkor.grouptester.domain;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * The Service interface defines common methods for services dealing with entities.
 *
 * @param <E> The type of entity handled by the service.
 */
public interface Service<E extends Entity> {

    /**
     * Retrieves an entity by its unique identifier (UUID).
     *
     * @param id The unique identifier of the entity.
     * @return The entity with the specified ID, or null if not found.
     */
    E get(UUID id);

    /**
     * Retrieves all entities of the specified type.
     *
     * @return A set containing all entities of the specified type.
     */
    Set<E> getAll();

    /**
     * Retrieves entities based on the provided filter predicate.
     *
     * @param filter The predicate used to filter entities.
     * @return A set containing entities that satisfy the filter predicate.
     */
    Set<E> getAll(Predicate<E> filter);

    /**
     * Adds a new entity to the service.
     *
     * @param entity The entity to be added.
     * @return The added entity.
     */
    E add(E entity);

    /**
     * Removes the specified entity from the service.
     *
     * @param entity The entity to be removed.
     * @return true if the removal is successful, false otherwise.
     */
    boolean remove(E entity);
}
