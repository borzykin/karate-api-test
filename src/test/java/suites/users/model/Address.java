package suites.users.model;

public class Address {
    private final String street;
    private final String suite;
    private final String city;
    private final String zipcode;

    private Address(String street, String suite, String city, String zipcode) {
        this.street = street;
        this.suite = suite;
        this.city = city;
        this.zipcode = zipcode;
    }

    public static Address valid() {
        return new Address(
                "Has No Name",
                "Apt. 123",
                "Electri",
                "54321-6789");
    }

    public static Address notValid() {
        return new Address(
                "street",
                "suite",
                "city",
                "zipcode");
    }
}
