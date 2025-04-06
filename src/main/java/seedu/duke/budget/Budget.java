package seedu.duke.budget;

import enums.Category;

import java.time.LocalDate;

public class Budget {
    private String name;
    private double totalAmount;
    private double remainingAmount;
    private LocalDate endDate;
    private Category category;

    public Budget(String name, double totalAmount, LocalDate endDate, Category category) {
        this.name = name;
        this.totalAmount = totalAmount;
        this.remainingAmount = totalAmount;
        this.endDate = endDate;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Category getCategory() {
        return category;
    }

    public void addAmount(double amount) {
        this.totalAmount += amount;
        this.remainingAmount += amount;
    }

    public void deductAmount(double amount) {
        this.remainingAmount -= amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalAmount(double totalAmount) {
        double difference = totalAmount - this.totalAmount;
        this.totalAmount = totalAmount;
        this.remainingAmount += difference;  // adjust remaining accordingly
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format(
                "Budget: %s\nCategory: %s\nEnd Date: %s\nTotal: $%.2f\nRemaining: $%.2f",
                name, category, endDate, totalAmount, remainingAmount
        );
    }
}
