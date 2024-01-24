package com.tritonkor.grouptester.persistence;

import com.github.javafaker.Faker;
import com.tritonkor.grouptester.persistence.entity.Generator;
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

        List<User> generatedUsers = Generator.generateUsers(10);

        for (User user : generatedUsers) {
            System.out.println(user);
        }

        List<Test> generatedTest = Generator.generateTests(5);

        for (Test test : generatedTest) {
            System.out.println(test);
        }

        //writeUsersToJsonFile(generatedUsers, "users.json");
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
