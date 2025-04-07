package seedu.duke.budget;

import enums.Category;
import enums.Currency;
import seedu.duke.Transaction;

import java.time.LocalDate;
import java.util.List;

public class Budget {
    private String name;
    private double totalAmount;
    private LocalDate endDate;
    private Category category;

    public Budget(String name, double totalAmount, LocalDate endDate, Category category) {
        this.name = name;
        this.totalAmount = totalAmount;
        this.endDate = endDate;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Category getCategory() {
        return category;
    }

    public void addAmount(double amount) {
        this.totalAmount += amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double calculateRemaining(List<Transaction> transactions) {
        double spent = 0;
        for (Transaction t : transactions) {
            if (t.getCategory() == this.category && t.getAmount() < 0) {
                double amountInSGD = t.getCurrency().convertTo(-t.getAmount(), Currency.SGD); // convert to SGD
                spent += amountInSGD;
            }
        }
        return totalAmount - spent;
    }


    @Override
    public String toString() {
        return String.format(
                "Budget: %s\nCategory: %s\nEnd Date: %s\nTotal: $%.2f",
                name, category, endDate, totalAmount
        );
    }
}
