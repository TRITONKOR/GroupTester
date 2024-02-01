package com.tritonkor.grouptester.persistence.repository;

import com.tritonkor.grouptester.persistence.entity.Entity;
import java.util.Set;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * The {@code Repository} interface defines the contract for managing entities in a data
 * repository.
 *
 * @param <E> The type of entity to be stored in the repository.
 */
public interface Repository<E extends Entity> {

    /**
     * Retrieves an entity by its unique identifier (ID).
     *
     * @param id The unique identifier of the entity.
     * @return An optional containing the entity if found, or an empty optional if not found.
     */
    Optional<E> findById(UUID id);

    /**
     * Retrieves all entities stored in the repository.
     *
     * @return A set of all entities in the repository.
     */
    Set<E> findAll();

    /**
     * Retrieves entities from the repository based on a given filter predicate.
     *
     * @param filter The predicate used to filter entities.
     * @return A set of entities that satisfy the given filter predicate.
     */
    Set<E> findAll(Predicate<E> filter);

    /**
     * Adds a new entity to the repository.
     *
     * @param entity The entity to be added.
     * @return The added entity.
     */
    E add(E entity);

    /**
     * Removes an entity from the repository.
     *
     * @param entity The entity to be removed.
     * @return {@code true} if the entity was removed successfully, {@code false} otherwise.
     */
    boolean remove(E entity);
}
