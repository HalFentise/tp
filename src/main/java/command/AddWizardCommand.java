package command;

import seedu.duke.TransactionManager;
import seedu.duke.Storage;
import seedu.duke.Transaction;
import enums.Category;
import enums.Currency;
import enums.Priority;
import enums.Status;
import ui.Ui;

import java.time.LocalDate;
import java.util.Scanner;

import static ui.ConsoleFormatter.*;

public class AddWizardCommand extends Command {

    @Override
    public void execute(TransactionManager transactions, Ui ui) {
        throw new UnsupportedOperationException("AddWizardCommand requires storage. Use the 3-argument version.");
    }


    public void execute(TransactionManager transactions, Ui ui, Storage storage) {
        Scanner scanner = new Scanner(System.in);
        printCenteredTitle("Add Wizard");
        printCenteredLine("Create a new transaction step by step");
        printCenteredLine("Type 'cancel' at any time to abort.");
        printLine();

        try {
            System.out.print("Transaction/Add> Description: ");
            String description = scanner.nextLine().trim();
            if (description.equalsIgnoreCase("cancel")) return;

            double amount;
            while (true) {
                System.out.print("Transaction/Add> Amount (use negative for expense): ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("cancel")) return;
                try {
                    amount = Double.parseDouble(input);
                    break;
                } catch (NumberFormatException e) {
                    printCenteredTitle("ERROR: Add Wizard");
                    printLeftAlignedLine("Invalid amount. Please enter a number.");
                    printLine();
                }
            }

            Currency currency;
            while (true) {
                System.out.print("Transaction/Add> Currency (SGD, USD, EUR): ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("cancel")) return;
                try {
                    currency = Currency.valueOf(input.toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    printCenteredTitle("ERROR: Add Wizard");
                    printLeftAlignedLine("Invalid currency.");
                    printLine();
                }
            }

            Category category = null;
            while (category == null) {
                System.out.println("Transaction/Add> Select category:");
                Category[] categories = Category.values();
                for (int i = 0; i < categories.length; i++) {
                    System.out.println((i + 1) + ". " + categories[i]);
                }
                System.out.print("Transaction/Add> Enter number: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("cancel")) return;
                try {
                    int index = Integer.parseInt(input);
                    if (index >= 1 && index <= categories.length) {
                        category = categories[index - 1];
                    } else {
                        printCenteredTitle("ERROR: Add Wizard");
                        printLeftAlignedLine("Invalid selection. Try again.");
                        printLine();
                    }
                } catch (NumberFormatException e) {
                    printCenteredTitle("ERROR: Add Wizard");
                    printLeftAlignedLine("Please enter a number.");
                    printLine();
                }
            }

            LocalDate date;
            while (true) {
                System.out.print("Transaction/Add> Date (yyyy-mm-dd) or leave blank for today: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("cancel")) return;
                if (input.isEmpty()) {
                    date = LocalDate.now();
                    break;
                }
                try {
                    date = LocalDate.parse(input);
                    break;
                } catch (Exception e) {
                    printCenteredTitle("ERROR: Add Wizard");
                    printLeftAlignedLine("Invalid date format.");
                    printLine();
                }
            }

            Priority priority;
            while (true) {
                System.out.print("Transaction/Add> Priority (LOW, MEDIUM, HIGH) or leave blank for LOW: ");
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("cancel")) return;
                if (input.isEmpty()) {
                    priority = Priority.LOW;
                    break;
                }
                try {
                    priority = Priority.valueOf(input.toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    printCenteredTitle("ERROR: Add Wizard");
                    printLeftAlignedLine("Invalid priority.");
                    printLine();
                }
            }

            int id = transactions.getNextAvailableId();
            Transaction transaction = new Transaction(id, description, amount, currency, category, date, Status.PENDING);
            transaction.setPriority(priority);

            while (true) {
                System.out.print("Transaction/Add> Mark as completed? (YES/no): ");
                String completedInput = scanner.nextLine().trim().toLowerCase();

                if (completedInput.equals("cancel")) return;
                if (completedInput.isEmpty() || completedInput.equals("yes") || completedInput.equals("y")) {
                    transaction.complete();
                    break;
                } else if (completedInput.equals("no") || completedInput.equals("n")) {
                    break;
                } else {
                    printCenteredTitle("ERROR: Add Wizard");
                    printLeftAlignedLine("Please enter 'yes', 'no', or press Enter to default to yes.");
                    printLine();
                }
            }




            transactions.addTransaction(transaction);

            ui.add(transaction);

        } catch (Exception e) {
            ui.showError("❌ Failed to add transaction: " + e.getMessage());
        }
    }
}
