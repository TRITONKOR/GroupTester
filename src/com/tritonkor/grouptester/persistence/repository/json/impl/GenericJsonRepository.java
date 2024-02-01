package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.tritonkor.grouptester.Main;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.exception.JsonFileIOException;
import com.tritonkor.grouptester.persistence.repository.Repository;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A generic implementation of the {@link Repository} interface for JSON storage of entities.
 *
 * @param <E> The type of entities (subtype of {@link Entity}).
 */
public class GenericJsonRepository<E extends Entity> implements Repository<E> {

    private final Gson gson;
    private final Path path;
    private final Type collectionType;

    private final Path currentDir;

    protected final Set<E> entities;

    /**
     * Constructs a {@code GenericJsonRepository} with the specified Gson instance, file path, and
     * collection type.
     *
     * @param gson           The Gson instance for JSON serialization and deserialization.
     * @param path           The file path for storing JSON data.
     * @param collectionType The type of the collection of entities.
     */
    public GenericJsonRepository(Gson gson, Path path, Type collectionType) {
        this.currentDir = findProjectLocation();

        this.gson = gson;
        this.path = path;
        this.collectionType = collectionType;

        entities = new HashSet<>(loadAll());
    }

    /**
     * Loads all entities from the JSON file.
     *
     * @return A set of entities loaded from the JSON file.
     * @throws JsonFileIOException If there is an error working with the JSON file.
     */
    private Set<E> loadAll() {
        try {
            checkDataDirectory();
            fileNotFound();
            var json = Files.readString(currentDir.resolve(path));
            return isValidJson(json) ? gson.fromJson(json, collectionType) : new HashSet<>();
        } catch (IOException e) {
            throw new JsonFileIOException("Error working with file %s."
                    .formatted(currentDir.resolve(path).getFileName()));
        }
    }

    /**
     * Checks if the input string is a valid JSON format.
     *
     * @param input The JSON format string.
     * @return {@code true} if the input is a valid JSON format, {@code false} otherwise.
     */
    private boolean isValidJson(String input) {
        try (JsonReader reader = new JsonReader(new StringReader(input))) {
            reader.skipValue();
            return reader.peek() == JsonToken.END_DOCUMENT;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Checks if the JSON file exists; if not, creates the file.
     *
     * @throws IOException If an I/O error occurs while creating the file.
     */
    private void fileNotFound() throws IOException {
        if (!Files.exists(currentDir.resolve(path))) {
            Files.createFile(currentDir.resolve(path));
        }
    }

    /**
     * Creates the data directory if it does not exist.
     *
     * @throws IOException If an I/O error occurs while creating the directory.
     */
    private void checkDataDirectory() throws IOException {

        Path newDirectoryPath = currentDir.resolve(JsonPathFactory.DATA.getDataPath());

        if (!Files.exists(newDirectoryPath)) {
            Files.createDirectory(newDirectoryPath);
        }
    }

    /**
     * Finds the location of the project.
     *
     * @return The path to the project location.
     */
    private Path findProjectLocation() {
        String currentPath = Main.class.getProtectionDomain().getCodeSource().getLocation()
                .getPath();

        try {
            currentPath = java.net.URLDecoder.decode(currentPath, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new File(currentPath).toPath().getParent();
    }

    /**
     * Gets the path to the JSON file.
     *
     * @return The path to the JSON file.
     */
    public Path getPath() {
        return currentDir.resolve(path);
    }

    /**
     * Retrieves the entity by its UUID identifier.
     *
     * @param id The UUID identifier of the entity.
     * @return An optional containing the entity if found, otherwise empty.
     */
    @Override
    public Optional<E> findById(UUID id) {
        return entities.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    /**
     * Retrieves all entities in the repository.
     *
     * @return A set containing all entities in the repository.
     */
    @Override
    public Set<E> findAll() {
        return entities;
    }

    /**
     * Retrieves all entities in the repository that satisfy the given predicate.
     *
     * @param filter The predicate to filter entities.
     * @return A set containing entities that satisfy the predicate.
     */
    @Override
    public Set<E> findAll(Predicate<E> filter) {
        return entities.stream().filter(filter).collect(Collectors.toSet());
    }

    /**
     * Adds an entity to the repository.
     *
     * @param entity The entity to be added.
     * @return The added entity.
     */
    @Override
    public E add(E entity) {
        entities.remove(entity);
        entities.add(entity);
        return entity;
    }

    /**
     * Removes an entity from the repository.
     *
     * @param entity The entity to be removed.
     * @return True if the entity was successfully removed, otherwise false.
     */
    @Override
    public boolean remove(E entity) {
        return entities.remove(entity);
    }
}
