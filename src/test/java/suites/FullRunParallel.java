package suites;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import integrations.mocks.Mocks;
import integrations.testrail.TestResultsParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FullRunParallel {

    @Test
    void testParallel() {
        if (System.getProperty("isMocked", "false").equalsIgnoreCase("true")) {
            Mocks.startMocks();
        }

        Results results = Runner.path("classpath:suites")
                .tags("~@ignore")
                .outputCucumberJson(true)
                .parallel(3);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
        printResults();
    }

    void printResults() {
        // demonstrates functionality of results parses that can be used for setting results via testrail API
        System.out.println(TestResultsParser.getTestResultsMap());
    }

}
