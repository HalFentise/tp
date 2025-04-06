package seedu.duke.budget;

import enumstructure.Category;
import ui.Ui;
import ui.ConsoleFormatter;
import seedu.duke.Storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class BudgetMode {
    public static void enter(Ui ui, BudgetList budgetList, Storage storage) {
        Scanner scanner = new Scanner(System.in);
        ConsoleFormatter.printLine();
        System.out.println("You're now in Budget Mode!");
        System.out.println("Type 'help' to see budget commands. Type 'exit' to return.");
        ConsoleFormatter.printLine();

        while (true) {
            System.out.print("budget> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                ConsoleFormatter.printLine();
                System.out.println("Exiting Budget Mode...");
                ConsoleFormatter.printLine();
                break;
            }

            try {
                parseBudgetCommand(input, ui, budgetList, storage);
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
    }

    private static void parseBudgetCommand(String input, Ui ui, BudgetList list, Storage storage) throws Exception {
        if (input.isBlank()) {
            return;
        }
        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();

        switch (command) {
        case "help":
            printHelp();
            break;
        case "set":
            handleSetBudget(ui, list, storage);
            break;
        case "list":
            list.printAllBudgets(ui);
            break;
        case "check":
            handleCheckBudget(parts, ui, list);
            break;
        case "add":
            handleAddAmount(parts, list, storage);
            break;
        case "deduct":
            handleDeductAmount(parts, list, storage);
            break;
        case "modify":
            handleModify(parts, list, ui, storage);
            break;
        case "delete":
            handleDelete(parts, list, storage);
            break;
        default:
            throw new Exception("Unknown command in Budget Mode. Type 'help' for available commands.");
        }
    }

    private static void handleSetBudget(Ui ui, BudgetList list, Storage storage) {
        Scanner scanner = new Scanner(System.in);
        ConsoleFormatter.printLine();
        System.out.println("Enter budget name (or 'cancel' to abort):");
        String name = scanner.nextLine().trim();
        if (name.equalsIgnoreCase("cancel")) {
            return;
        }

        double totalAmount;
        while (true) {
            System.out.println("Enter total amount (or 'cancel'):");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("cancel")) {
                return;
            }
            try {
                totalAmount = Double.parseDouble(input);
                if (totalAmount <= 0) {
                    throw new NumberFormatException();
                }
                break;
            } catch (NumberFormatException e) {
                ui.showError("Invalid amount. Please enter a positive number.");
            }
        }

        LocalDate endDate;
        while (true) {
            System.out.println("Enter end date (yyyy-MM-dd) or 'cancel':");
            String dateStr = scanner.nextLine().trim();
            if (dateStr.equalsIgnoreCase("cancel")) {
                return;
            }
            try {
                endDate = LocalDate.parse(dateStr);
                break;
            } catch (DateTimeParseException e) {
                ui.showError("Invalid date format. Try yyyy-MM-dd.");
            }
        }

        Category category;
        while (true) {
            System.out.println("Choose category:");
            Category[] categories = Category.values();
            for (int i = 0; i < categories.length; i++) {
                System.out.printf("%d. %s%n", i + 1, categories[i]);
            }
            System.out.print("Enter number (or 'cancel'): ");
            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("cancel")) {
                return;
            }
            try {
                int index = Integer.parseInt(choice);
                if (index >= 1 && index <= categories.length) {
                    category = categories[index - 1];
                    break;
                }
            } catch (NumberFormatException ignored) {
                ui.showError("Invalid selection.");
            }
        }

        Budget budget = new Budget(name, totalAmount, endDate, category);
        list.add(budget);
        storage.saveBudgets(list);
        System.out.println("✅ Budget added successfully.");
        ConsoleFormatter.printLine();
    }

    private static void handleCheckBudget(String[] parts, Ui ui, BudgetList list) throws Exception {
        if (parts.length < 2 || !parts[1].startsWith("i/")) {
            throw new Exception("Usage: check i/INDEX");
        }
        int index = Integer.parseInt(parts[1].substring(2).trim()) - 1;
        list.printBudgetDetail(index, ui);
    }

    private static void handleAddAmount(String[] parts, BudgetList list, Storage storage) throws Exception {
        if (!parts[1].matches("i/\\d+ a/\\d+(\\.\\d+)?")) {
            throw new Exception("Usage: add i/INDEX a/AMOUNT");
        }
        String[] tokens = parts[1].split(" ");
        int index = Integer.parseInt(tokens[0].substring(2)) - 1;
        double amount = Double.parseDouble(tokens[1].substring(2));
        list.get(index).addAmount(amount);
        storage.saveBudgets(list);
        ConsoleFormatter.printLine();
        System.out.println("✅ Added $" + amount + " to budget #" + (index + 1));
        ConsoleFormatter.printLine();
    }

    private static void handleDeductAmount(String[] parts, BudgetList list, Storage storage) throws Exception {
        if (!parts[1].matches("i/\\d+ a/\\d+(\\.\\d+)?")) {
            throw new Exception("Usage: deduct i/INDEX a/AMOUNT");
        }
        String[] tokens = parts[1].split(" ");
        int index = Integer.parseInt(tokens[0].substring(2)) - 1;
        double amount = Double.parseDouble(tokens[1].substring(2));
        list.get(index).deductAmount(amount);
        storage.saveBudgets(list);
        ConsoleFormatter.printLine();
        System.out.println("✅ Deducted $" + amount + " from budget #" + (index + 1));
        ConsoleFormatter.printLine();
    }

    private static void handleModify(String[] parts, BudgetList list, Ui ui, Storage storage) throws Exception {
        String[] tokens = parts[1].split(" ");
        int index = -1;
        String name = null;
        Double amount = null;
        LocalDate endDate = null;
        Category category = null;

        for (String token : tokens) {
            if (token.startsWith("i/")) {
                index = Integer.parseInt(token.substring(2)) - 1;
            } else if (token.startsWith("n/")) {
                name = token.substring(2);
            } else if (token.startsWith("a/")) {
                amount = Double.parseDouble(token.substring(2));
            } else if (token.startsWith("e/")) {
                endDate = LocalDate.parse(token.substring(2));
            } else if (token.startsWith("c/")) {
                category = Category.valueOf(token.substring(2).toUpperCase());
            }
        }

        if (index == -1) {
            throw new Exception("Missing budget index. Use i/INDEX");
        }

        Budget b = list.get(index);
        if (name != null) {
            b.setName(name);
        }
        if (amount != null) {
            b.setTotalAmount(amount);
        }
        if (endDate != null) {
            b.setEndDate(endDate);
        }
        if (category != null) {
            b.setCategory(category);
        }

        storage.saveBudgets(list);
        ConsoleFormatter.printLine();
        System.out.println("✅ Budget modified successfully.");
        list.printBudgetDetail(index, ui);
    }

    private static void handleDelete(String[] parts, BudgetList list, Storage storage) throws Exception {
        if (parts.length < 2 || !parts[1].startsWith("i/")) {
            throw new Exception("Usage: delete i/INDEX");
        }
        int index = Integer.parseInt(parts[1].substring(2)) - 1;
        if (index < 0 || index >= list.size()) {
            throw new Exception("Invalid index. Use 'list' to check your budgets.");
        }
        Budget removed = list.get(index);
        list.remove(index);
        storage.saveBudgets(list);
        ConsoleFormatter.printLine();
        System.out.println("🗑️ Deleted budget: " + removed.getName());
        ConsoleFormatter.printLine();
    }

    private static void printHelp() {
        ConsoleFormatter.printLine();
        System.out.println("📘 Budget Mode Commands:");
        System.out.println("- set: Interactively create a new budget");
        System.out.println("- list: List all budgets (basic info)");
        System.out.println("- check i/INDEX: View budget details");
        System.out.println("- add i/INDEX a/AMOUNT: Add to budget total + remaining");
        System.out.println("- deduct i/INDEX a/AMOUNT: Deduct from budget remaining");
        System.out.println("- modify i/INDEX [n/NAME] [a/AMOUNT] [e/DATE] [c/CATEGORY]: Modify fields");
        System.out.println("- delete i/INDEX: Delete a budget");
        System.out.println("- exit: Return to main menu");
        ConsoleFormatter.printLine();
    }
}
