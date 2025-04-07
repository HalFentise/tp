package command;

import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import enums.Category;
import enums.Currency;
import enums.Priority;
import ui.Ui;

import java.time.LocalDate;
import java.util.Scanner;

import static ui.ConsoleFormatter.*;

public class EditWizardCommand extends Command {
    @Override
    public void execute(TransactionManager transactions, Ui ui) {
        Scanner scanner = new Scanner(System.in);

        printCenteredTitle("Edit Wizard");
        printCenteredLine("Modify a transaction step by step");
        printCenteredLine("Type 'cancel' at any time to abort.");
        printLine();

        Transaction target = null;
        while (target == null) {
            System.out.print("Transaction/Edit> Enter Transaction ID: ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("cancel")) return;
            try {
                int id = Integer.parseInt(input);
                target = transactions.searchTransaction(id);
                if (target == null) {
                    printCenteredTitle("ERROR: Edit Wizard");
                    printLeftAlignedLine("Transaction not found.");
                    printLine();
                }
            } catch (Exception e) {
                printCenteredTitle("ERROR: Edit Wizard");
                printLeftAlignedLine("Invalid ID format.");
                printLine();
            }
        }

        System.out.println("Transaction/Edit> Choose field to edit:");
        String[] options = {"Description", "Amount", "Currency", "Category", "Date", "Priority"};
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }

        System.out.print("Transaction/Edit> Enter number: ");
        String choice = scanner.nextLine().trim();
        if (choice.equalsIgnoreCase("cancel")) return;

        try {
            int field = Integer.parseInt(choice);
            switch (field) {
                case 1 -> {
                    System.out.print("Transaction/Edit> New description: ");
                    String desc = scanner.nextLine();
                    if (desc.equalsIgnoreCase("cancel")) return;
                    target.setDescription(desc);
                    ui.printEdited(desc, 0);
                }
                case 2 -> {
                    System.out.print("Transaction/Edit> New amount: ");
                    String amtStr = scanner.nextLine();
                    if (amtStr.equalsIgnoreCase("cancel")) return;
                    double amount = Double.parseDouble(amtStr);
                    target.setAmount((int) amount);
                    ui.printEdited(amtStr, 2);
                }
                case 3 -> {
                    System.out.print("Transaction/Edit> New currency: ");
                    String currency = scanner.nextLine();
                    if (currency.equalsIgnoreCase("cancel")) return;
                    target.setCurrency(Currency.valueOf(currency.toUpperCase()));
                    ui.printEdited(currency, 3);
                }
                case 4 -> {
                    System.out.print("Transaction/Edit> New category: ");
                    String category = scanner.nextLine();
                    if (category.equalsIgnoreCase("cancel")) return;
                    target.setCategory(Category.valueOf(category.toUpperCase()));
                    ui.printEdited(category, 1);
                }
                case 5 -> {
                    System.out.print("Transaction/Edit> New date (yyyy-mm-dd): ");
                    String date = scanner.nextLine();
                    if (date.equalsIgnoreCase("cancel")) return;
                    target.setDate(LocalDate.parse(date));
                    printCenteredTitle("OK: Edit Wizard");
                    printLeftAlignedLine("Date updated.");
                    printLine();
                }
                case 6 -> {
                    System.out.print("Transaction/Edit> New priority (LOW, MEDIUM, HIGH): ");
                    String p = scanner.nextLine();
                    if (p.equalsIgnoreCase("cancel")) return;
                    target.setPriority(Priority.valueOf(p.toUpperCase()));
                    ui.printEdited(p, 99);
                }
                default -> {
                    printCenteredTitle("ERROR: Edit Wizard");
                printLeftAlignedLine("Invalid option.");
                printLine();
                }
            }
        } catch (Exception e) {
            printCenteredTitle("ERROR: Edit Wizard");
            printLeftAlignedLine("Syntax Error.");
            printLine();
        }
    }
}
