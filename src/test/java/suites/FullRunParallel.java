package suites;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import static org.junit.jupiter.api.Assertions.*;

import integrations.TestResultsParser;
import org.junit.jupiter.api.Test;

class FullRunParallel {

    @Test
    void testParallel() {
        Results results = Runner.path("classpath:suites")
                .tags("~@ignore")
                .outputCucumberJson(true)
                .parallel(5);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
        printResults();
    }

    void printResults() {
        // demonstrates functionality of results parses that can be used for setting results via testrail API
        System.out.println(TestResultsParser.getTestResultsMap());
    }

}
