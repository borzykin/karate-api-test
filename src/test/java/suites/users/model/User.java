package suites.users.model;

import com.google.gson.Gson;

public class User {
    private final String name;
    private final String username;
    private final String email;
    private final Address address;

    private User(String name, String username, String email, Address address) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
    }

    public static String validAsJson() {
        return new Gson().toJson(new User("Test User", "testuser", "test@user.com", Address.valid()));
    }

    public static String notValidAsJson() {
        return new Gson().toJson(new User("1234", "1234", "123.com", Address.notValid()));
    }
}
