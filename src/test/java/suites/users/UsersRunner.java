package suites.users;

import com.intuit.karate.junit5.Karate;

class UsersRunner {
    
    @Karate.Test
    Karate testUsers() {
        // it is possible to pass feature name to run() class to run only this specific feature
        return Karate.run().relativeTo(getClass());
    }    

}
