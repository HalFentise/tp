package seedu.duke.budget;

import java.util.ArrayList;
import ui.Ui;
import ui.ConsoleFormatter;

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
        ConsoleFormatter.printLine();
        System.out.println("🔍 Budget Details:");
        System.out.println("Name: " + b.getName());
        System.out.println("Target Amount: $" + b.getTotalAmount());
        System.out.println("Remaining: $" + b.getRemainingAmount());
        System.out.println("End Date: " + b.getEndDate());
        System.out.println("Category: " + b.getCategory());
        ConsoleFormatter.printLine();
    }

    public void printAllBudgets(Ui ui) {
        final int TOTAL_WIDTH = 81;
        final String HEADER_FORMAT = "| %-2s | %-14s | %9s | %-10s | %-10s |";
        final String ROW_FORMAT    = "| %2d | %-14s | %9.2f | %-10s | %-10s |";

        if (budgets.isEmpty()) {
            ConsoleFormatter.printLine();
            ConsoleFormatter.printLeftAlignedLine("No budgets found.");
            ConsoleFormatter.printLine();
            return;
        }

        String headerLine = String.format(HEADER_FORMAT, "#", "Name", "Amount", "End Date", "Category");
        int tableWidth = headerLine.length();
        int spaceInsideBox = TOTAL_WIDTH - 4;
        int sidePadding = (spaceInsideBox - tableWidth) / 2;

        ConsoleFormatter.printLine();

        int headerLeftPad = (TOTAL_WIDTH - headerLine.length()) / 2;
        System.out.println(" ".repeat(headerLeftPad) + headerLine);

        ui.printTableLine("-".repeat(tableWidth), sidePadding);

        for (int i = 0; i < budgets.size(); i++) {
            Budget b = budgets.get(i);
            String row = String.format(ROW_FORMAT,
                    i + 1,
                    b.getName(),
                    b.getTotalAmount(),
                    b.getEndDate().toString(),
                    b.getCategory());
            ui.printTableLine(row, sidePadding);
        }

        ConsoleFormatter.printLine();
    }

}
