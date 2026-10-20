package support;

/** Scenario-owned data. Never print passwords, phone numbers or email addresses to reports. */
public final class RegistrationUser {
    private final String firstName, lastName, email, phone, password;

    public RegistrationUser(String firstName, String lastName, String email, String phone, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public static RegistrationUser fromEnvironment() {
        return new RegistrationUser(required("N11_REGISTER_FIRST_NAME"),
            System.getenv().getOrDefault("N11_REGISTER_LAST_NAME", ""),
            required("N11_REGISTER_EMAIL"), required("N11_REGISTER_PHONE"), required("N11_REGISTER_PASSWORD"));
    }

    private static String required(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) throw new IllegalStateException("Missing environment variable: " + key);
        return value;
    }

    public String firstName() { return firstName; }
    public String lastName() { return lastName; }
    public String email() { return email; }
    public String phone() { return phone; }
    public String password() { return password; }
    @Override public String toString() { return "RegistrationUser[redacted]"; }
}
