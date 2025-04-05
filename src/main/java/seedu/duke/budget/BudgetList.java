package seedu.duke.budget;

import java.util.ArrayList;
import ui.Ui;

public class BudgetList {
    private ArrayList<Budget> budgets;

    public BudgetList() {
        budgets = new ArrayList<>();
    }

    public void add(Budget budget) {
        budgets.add(budget);
    }

    public Budget get(int index) {
        return budgets.get(index);
    }

    public void remove(int index) {
        budgets.remove(index);
    }

    public int size() {
        return budgets.size();
    }

    public ArrayList<Budget> getAll() {
        return budgets;
    }

    public boolean isEmpty() {
        return budgets.isEmpty();
    }

    public void clear() {
        budgets.clear();
    }

    public void printBudgetDetail(int index, Ui ui) {
        if (index < 0 || index >= budgets.size()) {
            ui.showError("Invalid budget index.");
            return;
        }

        Budget b = budgets.get(index);
        ui.showLine();
        System.out.println("🔍 Budget Details:");
        System.out.println("Name: " + b.getName());
        System.out.println("Target Amount: $" + b.getTotalAmount());
        System.out.println("Remaining: $" + b.getRemainingAmount());
        System.out.println("End Date: " + b.getEndDate());
        System.out.println("Category: " + b.getCategory());
        ui.showLine();
    }

    public void printAllBudgets(Ui ui) {
        ui.showLine();
        if (budgets.isEmpty()) {
            System.out.println("No budgets found.");
            ui.showLine();
            return;
        }

        System.out.println("📋 List of Budgets:");
        for (int i = 0; i < budgets.size(); i++) {
            Budget b = budgets.get(i);
            System.out.printf("%d. %s | $%.2f | Ends: %s | Category: %s%n",
                    i + 1,
                    b.getName(),
                    b.getTotalAmount(),
                    b.getEndDate(),
                    b.getCategory());
        }
        ui.showLine();
    }
}
