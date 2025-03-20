package seedu.duke;

import java.time.LocalDate;
import java.util.ArrayList;

public class Transaction {
    private final int id;
    private final int amount;
    private final Currency currency;
    private final LocalDate date;
    private final Status status;

    // changeable fields
    private String description;
    private Category category;
    private ArrayList<String> tags;
    private boolean isDeleted = false;
    private boolean isCompleted = false;

    //Constructor
    Transaction(int id, int amount, String description, Currency currency,
                Category category, LocalDate date, Status status) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.currency = currency;
        this.category = category;
        this.date = date;
        this.status = status;
        tags = new ArrayList<>();
    }

    Transaction(int id, String description, int amount, Currency currency, LocalDate date, Status status) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.status = status;
        this.tags = new ArrayList<>();
    }


    public String toString() {
        return "seedu.duke.Transaction id: " + id + "\namount: " + amount + "\ndescription: " + description;
    }

    //get method
    public int getId() {
        return id;
    }


    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public ArrayList<String> getTags() {
        return new ArrayList<>(tags);
    }

    public Status getStatus() {
        return status;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    //set method
    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void complete() {
        isCompleted = true;
    }

    public void notComplete() {
        isCompleted = false;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public boolean containsTag(String tag) {
        return tags.contains(tag);
    }

    public boolean isSameTransaction(Transaction otherTransaction) {
        if (this.id == otherTransaction.id) {
            return true;
        } else {
            return false;
        }
    }

    public void delete() {
        isDeleted = true;
    }

    public void recover() {
        isDeleted = false;
    }
}
