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

public class JsonRepositoryFactory extends RepositoryFactory {

    private final Gson gson;

    private UserJsonRepositoryImpl userJsonRepositoryImpl;
    private GroupJsonRepositoryImpl groupJsonRepositoryImpl;
    private TestJsonRepositoryImpl testJsonRepositoryImpl;
    private ResultJsonRepositoryImpl resultJsonRepositoryImpl;
    private ReportJsonRepositoryImpl reportJsonRepositoryImpl;

    private JsonRepositoryFactory() {
        // Адаптер для типу даних LocalDateTime при серіалізації/десеріалізації
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

        // Адаптер для типу даних LocalDate при серіалізації/десеріалізації
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

    public static JsonRepositoryFactory getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private <E extends Entity> void serializeEntities(Path path, Set<E> entities) {
        try (FileWriter writer = new FileWriter(path.toFile())) {
            // Скидуємо файлик, перед збереженням!
            writer.write("");
            // Перетворюємо колекцію користувачів в JSON та записуємо у файл
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
    public ReportRepository getReportRepository() { return reportJsonRepositoryImpl; }

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
