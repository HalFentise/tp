package seedu.duke;

public enum Currency {
    USD("United States Dollar"),
    SGD("Singapore Dollar"),
    CNY("Chinese Yuan"),
    EUR("Euro"),
    JPY("Japanese Yen"),
    GBP("British Pound");

    private final String fullName; // full name

    //Constructor
    Currency(String fullName) {
        this.fullName = fullName;
    }

    //Display full name
    String getFullName() {
        return fullName;
    }
}
