package suites.reqres;

import com.intuit.karate.junit5.Karate;

class ReqresRunner {
    
    @Karate.Test
    Karate testReqres() {
        return Karate.run().relativeTo(getClass());
    }    

}
