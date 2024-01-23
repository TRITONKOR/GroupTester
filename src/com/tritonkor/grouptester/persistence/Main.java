package com.tritonkor.grouptester.persistence;

import com.github.javafaker.Faker;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.entity.impl.Test.TestBuilder;
import com.tritonkor.grouptester.persistence.util.LocalDateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        List<User> generatedUsers = generateUsers(10);

        for (User user : generatedUsers) {
            System.out.println(user);
        }

        //Test test = Test.builder().id(UUID.randomUUID()).title("This name").countOfQuestionsle(12).questionsList(null).createdAt(LocalDate.now()).build();
        //System.out.println(test.toString());

        writeUsersToJsonFile(generatedUsers, "users.json");
    }
    public static List<User> generateUsers(int count) {
        List<User> users = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 0; i < count; i++) {
            UUID id = UUID.randomUUID();
            String username = faker.name().username();
            String email = faker.internet().emailAddress();
            String password = faker.internet().password();
            LocalDate birthday = faker.date()
                    .birthday()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            User user = new User(id, username, email, password, birthday);
            users.add(user);
        }

        return users;
    }

    public static void writeUsersToJsonFile(List<User> users, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Створюємо Gson з красивим виведенням
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                    .setPrettyPrinting().create();

            // Перетворюємо колекцію користувачів в JSON та записуємо у файл
            gson.toJson(users, writer);

            System.out.println("Колекцію користувачів збережено в файл " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
