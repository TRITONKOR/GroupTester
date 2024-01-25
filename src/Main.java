import static java.lang.System.out;

import com.tritonkor.grouptester.persistence.entity.Generator;
import com.tritonkor.grouptester.persistence.entity.impl.Result;
import com.tritonkor.grouptester.persistence.entity.impl.Test;
import com.tritonkor.grouptester.persistence.repository.RepositoryFactory;
import com.tritonkor.grouptester.persistence.repository.contracts.ResultRepository;
import com.tritonkor.grouptester.persistence.repository.contracts.TestRepository;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

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

    }
}
