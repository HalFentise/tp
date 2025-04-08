package enums;

/**
 * Represents the status of a transaction.
 * Each status reflects a different phase or outcome of a transaction's lifecycle.
 */
public enum Status {

    /**
     * The transaction has been created but is not yet completed.
     */
    PENDING("Pending", "The transaction has been created but is not yet completed"),

    /**
     * The transaction was successfully completed.
     */
    COMPLETED("Completed", "The transaction was successfully completed"),

    /**
     * The transaction failed due to some reason (e.g., insufficient funds).
     */
    FAILED("Failed", "The transaction failed due to some reason"),

    /**
     * The transaction was canceled by the user or the system.
     */
    CANCELED("Canceled", "The transaction was canceled by the user or system"),

    /**
     * The transaction has been refunded back to the user.
     */
    REFUNDED("Refunded", "The transaction has been refunded"),

    /**
     * The transaction is currently being processed and has not yet completed.
     */
    IN_PROGRESS("In Progress", "The transaction is being processed"),

    /**
     * The transaction is completed and has been paid.
     * This can be used as an alternate to {@code COMPLETED} for financial contexts.
     */
    PAID("Paid", "The transaction is completed ");

    private final String displayName; // Display name
    private final String description; // Description

    /**
     * Constructs a {@code Status} enum with a display name and description.
     *
     * @param displayName the user-friendly name of the status
     * @param description a short explanation of the status
     */
    Status(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    /**
     * Returns the description of this status.
     *
     * @return the description of the status
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the display name of this status.
     *
     * @return the display name of the status
     */
    @Override
    public String toString() {
        return displayName;
    }
}
