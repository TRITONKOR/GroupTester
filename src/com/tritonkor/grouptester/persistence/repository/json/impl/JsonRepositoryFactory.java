package com.tritonkor.grouptester.persistence.repository.json.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.tritonkor.grouptester.persistence.entity.Entity;
import com.tritonkor.grouptester.persistence.exception.JsonFileIOException;
import com.tritonkor.grouptester.persistence.repository.RepositoryFactory;
import com.tritonkor.grouptester.persistence.repository.contracts.GroupRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.ReportRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.ResultRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.TestRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.UserRepository;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;

/**
 * The {@code JsonRepositoryFactory} class is a concrete implementation of {@link RepositoryFactory}
 * that provides JSON-based repositories for various entities in the system.
 */
public class JsonRepositoryFactory extends RepositoryFactory {

    private final Gson gson;

    private UserJsonRepositoryImpl userJsonRepositoryImpl;
    private GroupJsonRepositoryImpl groupJsonRepositoryImpl;
    private TestJsonRepositoryImpl testJsonRepositoryImpl;
    private ResultJsonRepositoryImpl resultJsonRepositoryImpl;
    private ReportJsonRepositoryImpl reportJsonRepositoryImpl;

    private JsonRepositoryFactory() {
        // Adapter for LocalDateTime data type during serialization/deserialization
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class,
                (JsonSerializer<LocalDateTime>) (localDate, srcType, context) ->
                        new JsonPrimitive(
                                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm").format(localDate)));
        gsonBuilder.registerTypeAdapter(LocalDateTime.class,
                (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                        LocalDateTime.parse(json.getAsString(),
                                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                                        .withLocale(Locale.of("uk", "UA"))));

        // Adapter for LocalDate data type during serialization/deserialization
        gsonBuilder.registerTypeAdapter(LocalDate.class,
                (JsonSerializer<LocalDate>) (localDate, srcType, context) ->
                        new JsonPrimitive(
                                DateTimeFormatter.ofPattern("dd-MM-yyyy").format(localDate)));
        gsonBuilder.registerTypeAdapter(LocalDate.class,
                (JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                        LocalDate.parse(json.getAsString(),
                                DateTimeFormatter.ofPattern("dd-MM-yyyy")
                                        .withLocale(Locale.of("uk", "UA"))));

        gson = gsonBuilder.setPrettyPrinting().create();

        userJsonRepositoryImpl = new UserJsonRepositoryImpl(gson);
        groupJsonRepositoryImpl = new GroupJsonRepositoryImpl(gson);
        testJsonRepositoryImpl = new TestJsonRepositoryImpl(gson);
        resultJsonRepositoryImpl = new ResultJsonRepositoryImpl(gson);
        reportJsonRepositoryImpl = new ReportJsonRepositoryImpl(gson);
    }

    /**
     * Gets the singleton instance of {@code JsonRepositoryFactory}.
     *
     * @return The singleton instance of {@code JsonRepositoryFactory}.
     */
    public static JsonRepositoryFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Serializes a collection of entities to JSON and writes them to the specified file.
     *
     * @param path     The path to the file.
     * @param entities The collection of entities to be serialized and written.
     * @param <E>      The type of entities (subtype of {@link Entity}).
     * @throws JsonFileIOException If an error occurs while writing to the JSON file.
     */
    private <E extends Entity> void serializeEntities(Path path, Set<E> entities) {
        try (FileWriter writer = new FileWriter(path.toFile())) {
            // Clears the file before saving
            writer.write("");
            // Converts the collection of entities to JSON and writes to the file
            gson.toJson(entities, writer);

        } catch (IOException e) {
            throw new JsonFileIOException("Could not save data to json file. Details: %s"
                    .formatted(e.getMessage()));
        }
    }

    @Override
    public UserRepository getUserRepository() {
        return userJsonRepositoryImpl;
    }

    @Override
    public GroupRepository getGroupRepository() {
        return groupJsonRepositoryImpl;
    }

    @Override
    public TestRepository getTestRepository() {
        return testJsonRepositoryImpl;
    }

    @Override
    public ResultRepository getResultRepository() {
        return resultJsonRepositoryImpl;
    }

    @Override
    public ReportRepository getReportRepository() {
        return reportJsonRepositoryImpl;
    }

    /**
     * Commits changes to the underlying JSON files.
     */
    public void commit() {
        serializeEntities(userJsonRepositoryImpl.getPath(), userJsonRepositoryImpl.findAll());
        serializeEntities(groupJsonRepositoryImpl.getPath(), groupJsonRepositoryImpl.findAll());
        serializeEntities(testJsonRepositoryImpl.getPath(), testJsonRepositoryImpl.findAll());
        serializeEntities(resultJsonRepositoryImpl.getPath(), resultJsonRepositoryImpl.findAll());
        serializeEntities(reportJsonRepositoryImpl.getPath(), reportJsonRepositoryImpl.findAll());
    }

    private static class InstanceHolder {

        public static final JsonRepositoryFactory INSTANCE = new JsonRepositoryFactory();
    }
}
