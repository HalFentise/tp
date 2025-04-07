package seedu.duke;

import enums.Category;
import enums.Currency;
import enums.Status;
import ui.Ui;
import ui.ConsoleFormatter;

import java.time.LocalDate;
import java.util.Scanner;

public class SavingMode {

    public static void enter(Ui ui, FinancialGoal goal, TransactionManager transactions, Storage storage) {
        Scanner scanner = new Scanner(System.in);
        ConsoleFormatter.printCenteredTitle("Saving Mode");
        ConsoleFormatter.printCenteredLine("You're now in Saving Mode!");
        ConsoleFormatter.printCenteredLine("Type 'help' to see saving commands. Type 'exit' to return.");
        ConsoleFormatter.printLine();
        ui.printSavingOverview(goal);

        while (true) {
            System.out.print("saving> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                ConsoleFormatter.printLine();
                System.out.println("Exiting Saving Mode...");
                ConsoleFormatter.printLine();
                break;
            }

            try {
                parseSavingCommand(input, ui, goal, transactions, storage);
            } catch (Exception e) {
                ui.showError(e.getMessage());
            }
        }
    }

    private static void parseSavingCommand(String input, Ui ui, FinancialGoal goal,
                                           TransactionManager transactions, Storage storage) throws Exception {
        if (input.isBlank()) return;

        String[] parts = input.split(" ", 2);
        String command = parts[0].toLowerCase();

        switch (command) {
            case "help":
                printHelp();
                break;

            case "set":
                goal.createNewGoal(ui);
                storage.saveGoal(goal);
                break;

            case "list":
                ui.printGoal(goal);
                break;

            case "contribute":
            case "save":  // alias
                handleAddToSavings(parts, goal, transactions, storage, ui);
                break;

            case "deduct":
                handleSubtractFromSavings(parts, goal, transactions, storage, ui);
                break;

            default:
                throw new Exception("Unknown command in Saving Mode. Type 'help' for available commands.");
        }
    }

    private static void handleAddToSavings(String[] parts, FinancialGoal goal,
                                           TransactionManager transactions, Storage storage, Ui ui) throws Exception {
        if (parts.length < 2 || !parts[1].startsWith("a/")) {
            throw new Exception("Usage: contribute a/AMOUNT");
        }

        double addAmount = Double.parseDouble(parts[1].substring(2).trim());
        if (addAmount <= 0) {
            throw new Exception("Amount must be a positive number.");
        }

        double currentBalance = transactions.getCurrentBalanceInSGD();
        if (currentBalance < addAmount) {
            throw new Exception("Not enough balance! You only have: " + String.format("%.2f SGD", currentBalance));
        }

        int id = transactions.getNextAvailableId();
        Transaction t = new Transaction(id, "Savings", -addAmount, Currency.SGD, Category.SAVING,
                LocalDate.now(), Status.PENDING);
        t.complete();
        transactions.addTransaction(t);

        goal.addToSavings(addAmount);
        ui.showMessage("Added $" + String.format("%.2f", addAmount) + " to savings.");
        storage.saveGoal(goal);
        storage.saveTransactions(transactions.getTransactions());
    }

    private static void handleSubtractFromSavings(String[] parts, FinancialGoal goal,
                                                  TransactionManager transactions, Storage storage, Ui ui) throws Exception {
        if (parts.length < 2 || !parts[1].startsWith("a/")) {
            throw new Exception("Usage: deduct a/AMOUNT");
        }

        double subAmount = Double.parseDouble(parts[1].substring(2).trim());
        if (subAmount <= 0) {
            throw new Exception("Amount must be a positive number.");
        }

        double currentSavings = goal.getBalance();
        if (subAmount > currentSavings) {
            throw new Exception("Cannot deduct more than current savings. You only have $"
                    + String.format("%.2f", currentSavings));
        }

        int id = transactions.getNextAvailableId();
        Transaction t = new Transaction(id, "Savings", subAmount, Currency.SGD, Category.SAVING,
                LocalDate.now(), Status.PENDING);
        t.complete();
        transactions.addTransaction(t);

        goal.subFromSavings(subAmount);
        ui.showMessage("Deducted $" + String.format("%.2f", subAmount) + " from savings.");
        storage.saveGoal(goal);
        storage.saveTransactions(transactions.getTransactions());
    }

    private static void printHelp() {
        ConsoleFormatter.printLine();
        System.out.println("Saving Mode Commands:");
        System.out.println("- set: Create a new saving goal interactively");
        System.out.println("- list: Show current saving goal details");
        System.out.println("- contribute a/AMOUNT: Add funds to your goal (from your balance)");
        System.out.println("- save a/AMOUNT: (alias) Add funds to your goal");
        System.out.println("- deduct a/AMOUNT: Withdraw from your goal (added to your balance)");
        System.out.println("- exit: Return to main menu");
        ConsoleFormatter.printLine();
    }
}
