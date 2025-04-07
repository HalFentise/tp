package seedu.duke;

import enums.Category;
import enums.Currency;
import enums.Priority;
import enums.Status;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Represents a financial transaction with various attributes including amount, currency,
 * date, category, status, priority, tags, and recurrence.
 * Each transaction has a unique immutable ID and can be marked as deleted or completed.
 */
public class Transaction {
    private final int id;
    private double amount;
    private final Status status;
    private Currency currency;
    private LocalDate date;

    // Changeable fields
    private String description;
    private Category category;
    private Priority priority;
    private ArrayList<String> tags;
    private boolean isDeleted = false;
    private int recurringPeriod; // Repeats every X days, or once if 0
    private boolean isCompleted = false;

    /**
     * Creates a new Transaction with full details.
     *
     * @param id          Unique identifier of the transaction.
     * @param description Description of the transaction.
     * @param amount      Amount of money involved.
     * @param currency    Currency used in the transaction.
     * @param category    Category of the transaction.
     * @param date        Date of the transaction.
     * @param status      Status of the transaction (e.g., pending, confirmed).
     */
    public Transaction(int id, String description, double amount, Currency currency,
                       Category category, LocalDate date, Status status) {
        assert description != null : "Description cannot be null";
        assert currency != null : "Currency cannot be null";
        assert category != null : "Category cannot be null";
        assert date != null : "Date cannot be null";
        assert status != null : "Status cannot be null";
        assert amount >= 0 : "Amount should be non-negative";

        this.id = id;
        this.amount = amount;
        this.description = description;
        this.currency = currency;
        this.category = category;
        this.date = date;
        this.status = status;
        this.priority = Priority.LOW;
        tags = new ArrayList<>();
        recurringPeriod = 0;
    }

    /**
     * Creates a new Transaction without a specified category.
     * Intended for minimal transaction creation use cases.
     *
     * @param id          Unique identifier of the transaction.
     * @param description Description of the transaction.
     * @param amount      Amount of money involved.
     * @param currency    Currency used in the transaction.
     * @param date        Date of the transaction.
     * @param status      Status of the transaction.
     */
    public Transaction(int id, String description, double amount, Currency currency,
                       LocalDate date, Status status) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.status = status;
        this.priority = Priority.LOW;
        this.tags = new ArrayList<>();
        recurringPeriod = 0;
    }

    /**
     * Returns a CSV-style string representation of the transaction.
     *
     * @return Formatted string of transaction fields.
     */
    @Override
    public String toString() {
        return id + "," +
                description + "," +
                amount + "," +
                currency + "," +
                category + "," +
                priority + "," +
                status + "," +
                (date == null ? "N/A" : date.toString());
    }

    // ======================== Getters ========================

    /** @return Unique ID of the transaction. */
    public int getId() {
        return id;
    }

    /** @return Amount of the transaction. */
    public double getAmount() {
        return amount;
    }

    /** @return Description of the transaction. */
    public String getDescription() {
        return description;
    }

    /** @return Currency of the transaction. */
    public Currency getCurrency() {
        return currency;
    }

    /** @return Category assigned to the transaction. */
    public Category getCategory() {
        return category;
    }

    /** @return Date of the transaction. */
    //@@author Lukapeng77
    public LocalDate getDate() {
        return date;
    }

    /** @return Priority level of the transaction. */
    public Priority getPriority() {
        return priority;
    }
    //@@author

    /** @return List of tags associated with the transaction. */
    public ArrayList<String> getTags() {
        return new ArrayList<>(tags);
    }

    /** @return Status of the transaction. */
    public Status getStatus() {
        return status;
    }

    /** @return Whether the transaction is marked as deleted. */
    public boolean isDeleted() {
        return isDeleted;
    }

    /** @return Number of days for recurrence (0 = one-time). */
    public int getRecurringPeriod() {
        return recurringPeriod;
    }

    /** @return Whether the transaction is marked as completed. */
    public boolean isCompleted() {
        return isCompleted;
    }

    // ======================== Setters ========================

    /**
     * Updates the transaction description.
     * @param description New description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Updates the category of the transaction.
     * @param category New category.
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Sets the recurrence period.
     * @param recurringPeriod Number of days between each recurrence (0 = one-time).
     */
    public void setRecurringPeriod(int recurringPeriod) {
        this.recurringPeriod = recurringPeriod;
    }

    /**
     * Sets the amount of the transaction.
     * @param amount New amount.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Sets the currency of the transaction.
     * @param currency New currency.
     */
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    /**
     * Sets the transaction date.
     * @param date New date.
     */
    //@@author Lukapeng77
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Sets the priority of the transaction.
     * @param priority New priority level.
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    //@@author

    // ======================== Status Methods ========================

    /** Marks the transaction as completed. */
    public void complete() {
        isCompleted = true;
    }

    /** Unmarks the transaction as completed. */
    public void notComplete() {
        isCompleted = false;
    }

    /** Marks the transaction as deleted. */
    public void delete() {
        isDeleted = true;
    }

    /** Recovers the transaction from deleted state. */
    public void recover() {
        isDeleted = false;
    }

    public void convertTo(Currency currency) {
        double toSGD = 1 / this.currency.getRate();
        this.amount = currency.getRate() * toSGD;
        this.currency = currency;
    }
}
