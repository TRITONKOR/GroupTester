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

public class GenericJsonRepository<E extends Entity> implements Repository<E> {

    private final Gson gson;
    private final Path path;
    private final Type collectionType;

    private final Path currentDir;

    protected final Set<E> entities;

    public GenericJsonRepository(Gson gson, Path path, Type collectionType) {
        this.currentDir = findProjectLocation();

        this.gson = gson;
        this.path = path;
        this.collectionType = collectionType;

        entities = new HashSet<>(loadAll());
    }

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
     * Перевірка на валідність формату даних JSON.
     *
     * @param input JSON у форматі рядка.
     * @return результат перевірки.
     */
    private boolean isValidJson(String input) {
        try (JsonReader reader = new JsonReader(new StringReader(input))) {
            reader.skipValue();
            return reader.peek() == JsonToken.END_DOCUMENT;
        } catch (IOException e) {
            return false;
        }
    }

    private void fileNotFound() throws IOException {
        if (!Files.exists(currentDir.resolve(path))) {
            Files.createFile(currentDir.resolve(path));
        }
    }

    private void checkDataDirectory() throws IOException {

        Path newDirectoryPath = currentDir.resolve(JsonPathFactory.DATA.getDataPath());

        if (!Files.exists(newDirectoryPath)) {
            Files.createDirectory(newDirectoryPath);
        }
    }

    private Path findProjectLocation() {
        String currentPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        try {
            currentPath = java.net.URLDecoder.decode(currentPath, "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new File(currentPath).toPath().getParent();
    }

    public Path getPath() {
        return currentDir.resolve(path);
    }

    @Override
    public Optional<E> findById(UUID id) {
        return entities.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    @Override
    public Set<E> findAll() {
        return entities;
    }

    @Override
    public Set<E> findAll(Predicate<E> filter) {
        return entities.stream().filter(filter).collect(Collectors.toSet());
    }

    @Override
    public E add(E entity) {
        entities.remove(entity);
        entities.add(entity);
        return entity;
    }

    @Override
    public boolean remove(E entity) {
        return entities.remove(entity);
    }

}
