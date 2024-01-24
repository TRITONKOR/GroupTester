import com.tritonkor.grouptester.persistence.entity.Generator;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.util.LocalDateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Test> generatedTests = Generator.generateTests(5);

        for (Test test : generatedTests) {
            System.out.println(test);
        }

        List<Group> generatedGroups = Generator.generateGroups(3);

        for (Group group : generatedGroups) {
            System.out.println(group);
        }

        writeUsersToJsonFile(generatedTests, "tests.json");
        writeUsersToJsonFile(generatedGroups, "groups.json");
    }

    public static <T> void writeUsersToJsonFile(List<T> list, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            // Створюємо Gson з красивим виведенням
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                    .setPrettyPrinting().create();

            // Перетворюємо колекцію користувачів в JSON та записуємо у файл
            gson.toJson(list, writer);

            System.out.println("Колекцію користувачів збережено в файл " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
