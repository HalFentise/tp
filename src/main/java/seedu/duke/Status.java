package seedu.duke;

public enum Status {
    PENDING("Pending", "The transaction has been created but is not yet completed"),
    COMPLETED("Completed", "The transaction was successfully completed"),
    FAILED("Failed", "The transaction failed due to some reason"),
    CANCELED("Canceled", "The transaction was canceled by the user or system"),
    REFUNDED("Refunded", "The transaction has been refunded"),
    IN_PROGRESS("In Progress", "The transaction is being processed");

    private final String displayName; // Display name
    private final String description; // Description

    // Constructor
    Status(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    // Get display name
    public String getDisplayName() {
        return displayName;
    }

    // Get description
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
