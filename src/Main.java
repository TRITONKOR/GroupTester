import static java.lang.System.out;

import com.github.javafaker.Faker;
import com.tritonkor.grouptester.domain.Generator;
import com.tritonkor.grouptester.domain.impl.ReportServiceImpl;
import com.tritonkor.grouptester.domain.impl.ResultServiceImpl;
import com.tritonkor.grouptester.domain.impl.TestServiceImpl;
import com.tritonkor.grouptester.persistence.entity.impl.Answer;
import com.tritonkor.grouptester.persistence.entity.impl.Grade;
import com.tritonkor.grouptester.persistence.entity.impl.Group;
import com.tritonkor.grouptester.persistence.entity.impl.Question;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.entity.impl.User;
import com.tritonkor.grouptester.persistence.entity.impl.User.Role;
import com.tritonkor.grouptester.persistence.repository.RepositoryFactory;
import com.tritonkor.grouptester.persistence.repository.contracts.ResultRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.ReportRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.TestRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        /*
        Set<Test> tests = Generator.generateTests(5);
        Set<Result> results = Generator.generateResults(5);


        RepositoryFactory jsonRepositoryFactory = RepositoryFactory.getRepositoryFactory(RepositoryFactory.JSON);

        TestRepository testRepository = jsonRepositoryFactory.getTestRepository();
        ResultRepository resultRepository = jsonRepositoryFactory.getResultRepository();

        for(Test test : tests) {
            testRepository.add(test);
        }

        for(Result result : results) {
            resultRepository.add(result);
        }

        testRepository.findAll().forEach(out::println);
        out.println("----------------");
        resultRepository.findAll().forEach(out::println);

        jsonRepositoryFactory.commit();
        */
        RepositoryFactory jsonRepositoryFactory = RepositoryFactory.getRepositoryFactory(RepositoryFactory.JSON);

        TestRepository testRepository = jsonRepositoryFactory.getTestRepository();
        ResultRepository resultRepository = jsonRepositoryFactory.getResultRepository();
        ReportRepository reportRepository = jsonRepositoryFactory.getReportRepository();

        TestServiceImpl testService = new TestServiceImpl(testRepository);
        ReportServiceImpl reportService = new ReportServiceImpl(reportRepository);
        ResultServiceImpl resultService = new ResultServiceImpl(resultRepository);

        Test test = null;
        Set<Test> tests = Generator.generateTests(1);
        for(Test testg : tests) {
            test = testg;
        }

        Faker faker = new Faker();
        LocalDate birthday = faker.date()
                .birthday()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        User user = User.builder().id(UUID.randomUUID()).username("babumbybumbybs")
                .email("baldumaldubr@gmail.com").password("fd%g4gdgd._").birthday(birthday).role(
                        Role.GENERAL).build();
        Group group = Group.builder().id(UUID.randomUUID()).name("dada").users(null).createdAt(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)).build();

        group.addObserver(user);
        group.notifyObservers(test.getTitle());

        Set<Answer> userAnswers = new HashSet<>();
        for(Question question : test.getQuestionsList()) {
            out.println("Введіть номер відповіді");
            out.println(question.toString());
            for(Answer answer : question.getAnswers()) {
                out.println(answer);
            }

            userAnswers.add(testService.getUserAnswer((() -> {
                Scanner scanner = new Scanner(System.in);
                return scanner.nextInt();
            }) , question));
        }
        Set<Grade> grades = new HashSet<>();
        grades.add(testService.calculateGrade(test, userAnswers));
        resultRepository.add(resultService.makeResult(user.getUsername(), test.getTitle(), testService.calculateGrade(test, userAnswers)));
        reportRepository.add(reportService.makeReport(group.getName(), test.getTitle(),grades));
        jsonRepositoryFactory.commit();
    }
}
