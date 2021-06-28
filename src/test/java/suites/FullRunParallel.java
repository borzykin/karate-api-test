package suites;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import integrations.mocks.Mocks;
import integrations.testrail.TestResultsParser;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FullRunParallel {

    @BeforeAll
    void setUp() {
        if (System.getProperty("mocked", "false").equalsIgnoreCase("true")) {
            Mocks.startMocks();
        }
    }

    @Test
    void testParallel() {
        Results results = Runner.path("classpath:suites")
                .tags("~@ignore")
                .outputCucumberJson(false)
                .parallel(3);
        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }

    @AfterAll
    void tearDown() {
        printResults();
        if (System.getProperty("mocked", "false").equalsIgnoreCase("true")) {
            Mocks.stopMocks();
        }
    }

    void printResults() {
        // demonstrates functionality of results parser that can be used for setting results via testrail API
        System.out.println(TestResultsParser.getTestResultsMap());
    }

}
