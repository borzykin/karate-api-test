package suites.testelium;

import com.intuit.karate.junit5.Karate;
import integrations.mocks.Mocks;

class TesteliumRunner {
    
    @Karate.Test
    Karate testTestelium() {
        Mocks.startMocks();
        return Karate.run().relativeTo(getClass()).tags("~@ignore");
    }    

}
