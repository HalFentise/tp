package ui;

import static ui.ConsoleFormatter.printLine;
import static ui.ConsoleFormatter.printCenteredLine;
import static ui.ConsoleFormatter.printCenteredTitle;
import static ui.ConsoleFormatter.printLeftAlignedLine;

import enums.Category;
import enums.Currency;
import seedu.duke.FinancialGoal;
import seedu.duke.Transaction;
import seedu.duke.TransactionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.List;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

import static constant.Constant.*;
import enums.Currency;
import enums.Category;


public class Ui {
    private final Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

    public String readCommand() {
        try {
            if (!scanner.hasNextLine()) {
                return "";
            }
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            System.out.println("Error: No input found");
            return "";
        }
    }


    public void printWelcomeMessage() {
        printLine();
        printCenteredLine(".  .    ,    .  .    __.                 ");
        printCenteredLine("|\\ | _ -+- _ |  |._.(__  _..  ,*._  _  __");
        printCenteredLine("| \\|(_) | (/,|__|[  .__)(_] \\/ |[ )(_]_)");
        printCenteredLine("                                  ._|");
        printCenteredLine("");
        printCenteredLine("Hello! This is NoteUrSavings here!");
        printCenteredLine("What can I do for you?");
        printLine();
    }

    public void help() {
        printCenteredTitle("NoteUrSavings - Help Menu");

        printLeftAlignedLine("General Commands:");
        printLeftAlignedLine("  help                - Show this help menu");
        printLeftAlignedLine("  exit                - Exit the application");

        printLine();
        printLeftAlignedLine("Transaction Commands:");
        printLeftAlignedLine("  add                 - Start guided wizard to add a transaction");
        printLeftAlignedLine("  edit                - Edit a transaction step by step (wizard)");
        printLeftAlignedLine("  status              - Mark/unmark transaction complete/incomplete");
        printLeftAlignedLine("  list                - Show all transactions");
        printLeftAlignedLine("  view <id>           - View full details of a specific transaction");
        printLeftAlignedLine("  search <text>       - Search transactions by keyword");
        printLeftAlignedLine("  delete <id>         - Delete a transaction by ID");

        printLine();
        printLeftAlignedLine("Statistics & Balance:");
        printLeftAlignedLine("  stats               - Show overview of your finances by category/status");
        printLeftAlignedLine("  balance             - Show total balance (based on completed transactions)");
        printLeftAlignedLine("  currency            - View all exchange rates to SGD");
        printLeftAlignedLine("  currency XXX RATE   - Update exchange rate for currency XXX (e.g. USD 0.75)");

        printLine();
        printLeftAlignedLine("Saving Mode:");
        printLeftAlignedLine("  saving              - Enter interactive Saving Mode");
        printLeftAlignedLine("    - set              - Create or update saving goal");
        printLeftAlignedLine("    - list             - Show saving goal details");
        printLeftAlignedLine("    - contribute a/X   - Add funds from balance (simulate expense)");
        printLeftAlignedLine("    - deduct a/X       - Withdraw from savings (simulate income)");
        printLeftAlignedLine("    - exit             - Return to main menu");

        printLine();
        printLeftAlignedLine("Budget Mode:");
        printLeftAlignedLine("  budget              - Enter interactive Budget Mode (not detailed here)");

        printLine();
        printCenteredLine("Type commands directly. Fields will be prompted in wizard mode.");
        printLine();
    }

    public void printExit() {
        printLine();
        printCenteredLine("Goodbye! Hope to see you again!");
        printLine();
    }

    public void showError(String message) {
        printLine();
        printLeftAlignedLine("Failed: " + message);
        printLine();
    }


    //@@author Lukapeng77

    /**
     * Prints a message indicating a transaction has been deleted, along with the updated transaction count.
     *
     * @param transaction The transaction that was deleted.
     * @param count       The number of transactions remaining after deletion.
     */
    public static void printDeleteTask(Transaction transaction, int count) {
        printLine();
        System.out.println("Noted. I've removed this transaction:");
        System.out.println(transaction);
        System.out.printf("Now you have %d transactions in the list.%n", count);
        printLine();
    }

    /**
     * Prints the result of a currency conversion from one currency to another.
     *
     * @param originalAmount  The original amount before conversion.
     * @param from            The original currency.
     * @param convertedAmount The amount after conversion.
     * @param to              The target currency.
     */
    public void printConversion(double originalAmount, Currency from, double convertedAmount, Currency to) {
        printLine();
        System.out.printf("Converted %.2f %s to %.2f %s%n",
                originalAmount, from.name(), convertedAmount, to.name());
        printLine();
    }

    //@@author

    /**
     * Prints a confirmation message indicating that all transactions have been cleared.
     */
    public void printClear() {
        printLine();
        System.out.println("All transactions have been cleared!");
        printLine();
    }

    //@@author Lukapeng77

    /**
     * Prints a list of upcoming transactions that match the given description and have a due date.
     *
     * @param upcomingTransactions A list of upcoming transactions.
     * @param description          The description to filter transactions by.
     */
    public void listNotification(ArrayList<Transaction> upcomingTransactions, String description) {
        printLine();
        if (upcomingTransactions.isEmpty()) {
            System.out.println("No upcoming expenses.");
        } else {
            System.out.println("Upcoming Expenses:");
            for (Transaction transaction : upcomingTransactions) {
                if (transaction.getDescription().equals(description) && transaction.getDate() != null) {
                    System.out.println("- " + transaction.getDescription() + " of " + transaction.getAmount() + " "
                            + transaction.getCurrency() + " in category " + transaction.getCategory() + " is due on "
                            + transaction.getDate());
                }
            }
        }
        printLine();
    }

    /**
     * Prints a list of all upcoming transactions with due dates.
     *
     * @param upcomingTransactions A list of upcoming transactions.
     */
    public void listNotifications(ArrayList<Transaction> upcomingTransactions) {
        if (upcomingTransactions.isEmpty()) {
            System.out.println("There are no upcoming transactions for now.");
            return;
        }
        boolean hasUpcoming = false;
        for (Transaction transaction : upcomingTransactions) {
            if (transaction.getDate() != null) {
                if (!hasUpcoming) {
                    System.out.println("Upcoming Expenses:");
                    hasUpcoming = true;
                }
                System.out.println("- " + transaction.getDescription() + " of " + transaction.getAmount() + " "
                        + transaction.getCurrency() + " in category " + transaction.getCategory() + " is due on "
                        + transaction.getDate());
            }
        }
        if (!hasUpcoming) {
            System.out.println("No upcoming expenses for now.");
        }
    }

    /**
     * Prints the priority setting for a specified transaction.
     *
     * @param transactions The list of transactions.
     * @param index        The index of the transaction whose priority is being set.
     */
    public void printPriority(ArrayList<Transaction> transactions, int index) {
        printLine();
        if (transactions.isEmpty()) {
            System.out.println("Please add a transaction first before you set the priority!");
        } else {
            System.out.println("Priority is set to " +
                    transactions.get(index).getPriority() + " for current transaction.");
        }
        printLine();
    }

    /**
     * Lists all transactions that have a high priority setting.
     *
     * @param upcomingTransactions A list of upcoming transactions to check for high priority.
     */
    public void listPriorities(ArrayList<Transaction> upcomingTransactions) {
        String defaultPriority = "HIGH";
        boolean hasHighPriority = false;
        for (Transaction transaction : upcomingTransactions) {
            if (transaction.getPriority() != null && transaction.
                    getPriority().toString().equalsIgnoreCase(defaultPriority)) {
                if (!hasHighPriority) {
                    System.out.println("Following transactions have the high priority:");
                    hasHighPriority = true;
                }
                System.out.println("- " + transaction.getDescription() + " " + transaction.getAmount() + " "
                        + transaction.getCurrency() + " in category " + transaction.getCategory());
            }
        }
        if (!hasHighPriority) {
            System.out.println("No high priority transactions found.");
        }
    }

    /**
     * Prints a summary of transactions between a given date range, including total expenses.
     *
     * @param transactions A list of transactions to summarize.
     * @param start        The start date of the summary period.
     * @param end          The end date of the summary period.
     */
    public void printSummary(List<Transaction> transactions, LocalDate start, LocalDate end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Expense Summary from " + start.format(formatter) + " to " + end.format(formatter));
        System.out.println("--------------------------------------------------");
        for (Transaction t : transactions) {
            System.out.printf("%s | %s | %.2f\n", t.getDate(), t.getDescription(), t.getAmount());
        }
        System.out.println("--------------------------------------------------");
    }
    //@@author

    /*public void printTransactions(ArrayList<Transaction> transactions) {
        printLine();
    }*/

//@@author HalFentise
    public void printTransactions(ArrayList<Transaction> transactions) {
        if (transactions.isEmpty()) {
            System.out.println("No transaction found.");
            printLine();
            return;
        }
        System.out.println("Here is the list of transactions:");
        printTransactionsTable(transactions);
        printLine();
    }

    public void printTransaction(Transaction transaction) {
        System.out.println(transaction);
    }

    public void printTransactionsTable(List<Transaction> transactions) {
        final int totalWidth = 121;
        final String innerHeaderFormat = "| %-2s | %-15s | %-9s | %-19s | %-9s | %-10s | %-9s | %-8s |";
        final String innerRowFormat = "| %2d | %-15s | %-9.2f | %-19s | %-9s | %-10s | %-9s | %-8s |";

        String sampleHeader = String.format(innerHeaderFormat,
                "ID", "Description", "Amount", "Currency", "Category", "Date", "Completed", "Priority");

        int tableWidth = sampleHeader.length(); // ~64
        int spaceInsideBox = totalWidth - 4;   // 外框两侧 || 各占2
        int sidePadding = (spaceInsideBox - tableWidth) / 2;

        // 打印顶边框
        printLine();

        if (transactions.isEmpty()) {
            printLeftAlignedLine("No transaction found.");
            printLine();
            return;
        }

        // 打印表头
        printTableLine(sampleHeader, sidePadding);

        // 表头下横线
        printTableLine("-".repeat(tableWidth), sidePadding);

        // 每一行打印
        for (Transaction t : transactions) {
            String completedMark = t.getRecurringPeriod() > 0 ? "  R (" + t.getRecurringPeriod() + ")"
                    : t.isCompleted() ? "    Y" : "    N";
            String row = String.format(innerRowFormat,
                    t.getId(),
                    limitWithEllipsis(t.getDescription()),
                    t.getAmount(),
                    t.getCurrency().toString(),
                    t.getCategory().toString(),
                    t.getDate() == null ? "N/A" : t.getDate().toString(),
                    completedMark,
                    t.getPriority().toString());

            printTableLine(row, sidePadding);

        }
        // 打印底边框
        printLine();
    }

    private static String limitWithEllipsis(String input) {
        if (input == null) {
            return "";
        }
        if (input.length() <= 15) {
            return input;
        }
        return input.substring(0, 15 - 3) + "...";
    }


    /**
     * 打印表格行，包裹 || 并居中填充空格
     */
    public void printTableLine(String content, int sidePadding) {
        final int totalWidth = 121;
        int contentWidth = totalWidth - 4;
        int rightPadding = contentWidth - sidePadding - content.length();
        String line = "| " + " ".repeat(sidePadding) + content + " ".repeat(Math.max(0, rightPadding)) + " |";
        System.out.println(line);
    }

    //@@author HalFentise
    private String trimToFit(String content, int maxLength) {
        if (content.length() <= maxLength) {
            return content;
        } else if (maxLength >= 3) {
            return content.substring(0, maxLength - 3) + "...";
        } else {
            return content.substring(0, maxLength);
        }
    }

    public void tickTransaction(Transaction transaction) {
        printLine();
        System.out.println("I have ticked the following transaction:");
        printTransaction(transaction);
        printLine();
    }

    public void unTickTransaction(Transaction transaction) {
        printLine();
        System.out.println("I un tick the following transaction:");
        printTransaction(transaction);
        printLine();
    }

    public void add(Transaction transaction) {
        printLine();
        System.out.println("I have added the following transaction to the list:");
        printTransaction(transaction);
        printLine();
    }

    //@@author yangyi-zhu

    /**
     * Prints a message based on whether the search was by index or by keyword.
     *
     * @param isIndex True if the search was by transaction index; false if by keyword.
     */
    public void search(boolean isIndex) {
        if (isIndex) {
            System.out.println("I have searched the transaction with the given index.");
        } else {
            System.out.println("I have searched the transactions containing the keywords.");
        }
    }

    /**
     * Sets the recurring period for a given transaction and prints confirmation.
     *
     * @param transaction     The transaction to modify.
     * @param recurringPeriod The number of days for recurrence; set to 0 or less to disable.
     */
    public void setPeriod(Transaction transaction, int recurringPeriod) {
        printLine();
        if (recurringPeriod > 0) {
            System.out.println("I have set the given transaction to recur every"
                    + (recurringPeriod == 1 ? " day." : " " + recurringPeriod + " days."));
        } else {
            System.out.println("I have disabled this transaction from recurring.");
        }
        printTransaction(transaction);
        printLine();
    }

    /**
     * Prints the details of a financial goal.
     *
     * @param goal The goal to be printed.
     */
    public void printGoal(FinancialGoal goal) {
        printLine();
        System.out.println(goal);
        printLine();
    }

    /**
     * Prints a confirmation message for updating a goal's target amount.
     *
     * @param goal The goal to update.
     */
    public void setGoalTarget(FinancialGoal goal) {
        printLine();
        System.out.println("I have updated your target to: " + goal.getTargetAmount());
        printLine();
    }

    /**
     * Prints a confirmation message for updating a goal's description.
     *
     * @param goal The goal to update.
     */
    public void setGoalDescription(FinancialGoal goal) {
        printLine();
        System.out.println("I have updated your description to:\n" + goal.getDescription());
        printLine();
    }

    /**
     * Prints a confirmation message for updating a goal's title/name.
     *
     * @param goal The goal to update.
     */
    public void setGoalTitle(FinancialGoal goal) {
        printLine();
        System.out.println("I have updated your goal to:\n" + goal.getGoal());
        printLine();
    }

    /**
     * Prompts the user to confirm whether they want to create a new goal.
     */
    public static void createGoalConfirm() {
        printLine();
        System.out.println("Want to set a new goal (Y/N)? ");
        printLine();
    }

    /**
     * Prompts the user to input the name of a new goal.
     */
    public static void createGoalName() {
        System.out.println("Name of new goal:");
        printLine();
    }

    /**
     * Prompts the user to input the target amount of a new goal.
     */
    public static void createGoalTarget() {
        System.out.println("Target amount of new goal:");
        printLine();
    }

    /**
     * Prompts the user to input the description of a new goal.
     */
    public static void createGoalDescription() {
        printLine();
        System.out.println("Description of new goal:");
        printLine();
    }

    /**
     * Displays a message indicating the goal has been successfully created.
     */
    public static void createGoalSuccess() {
        printLine();
        System.out.println("Goal successfully created\nRun 'goal' to see it!");
        printLine();
    }

    /**
     * Displays a message indicating the goal creation was aborted.
     */
    public static void createGoalAborted() {
        printLine();
        System.out.println("Goal creation cancelled by user.");
        printLine();
    }

    /**
     * Subtracts a specified amount from savings and displays a warning if the balance is negative.
     *
     * @param amount        The amount to subtract.
     * @param currentAmount The resulting balance after subtraction.
     */
    public static void subFromSavings(double amount, double currentAmount) {
        printLine();
        System.out.println("Subtracted " + amount + " from your savings.");
        if (currentAmount < 0) {
            System.out.println("Warning. You currently have a negative balance.");
        }
        printLine();
    }

    /**
     * Checks and prints the goal status based on current savings and target.
     *
     * @param currentAmount The current balance saved.
     * @param targetAmount  The savings target to reach.
     * @return True if the goal is achieved; false otherwise.
     */
    public static boolean printGoalStatus(double currentAmount, double targetAmount) {
        printLine();
        if (currentAmount >= targetAmount) {
            System.out.println("You have achieved the goal! Congratulations!");
            return true;
        }
        System.out.println("You're " + currentAmount + " out of " + targetAmount + ". Good luck!");
        printLine();
        return false;
    }

    /**
     * Prints a confirmation message after editing a transaction.
     *
     * @param value  The new value of the attribute.
     * @param typeId The type of attribute edited: 0=desc, 1=category, 2=amount, 3=currency.
     */
    public void printEdited(String value, int typeId) {
        String type;
        switch (typeId) {
        case 0:
            type = "description";
            break;
        case 1:
            type = "category";
            break;
        case 2:
            type = "amount";
            break;
        case 3:
            type = "currency";
            break;
        default:
            type = "";
        }

        printLine();
        System.out.println("Done! The " + type
                + " of the target transaction has been updated to:\n"
                + (typeId == 3 ? Currency.valueOf(value).toString()
                : (typeId == 2) ? Double.parseDouble(value) : value));
        printLine();
    }

    /**
     * Displays a list of upcoming recurring transactions, if any.
     *
     * @param transactions A list of recurring transactions.
     */
    public static void printRecurringTransactions(ArrayList<Transaction> transactions) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        printLine();
        if (transactions.isEmpty()) {
            System.out.println("You have no recurring payments ahead.");
            printLine();
            return;
        }
        System.out.println("Here is a list of your upcoming recurring payments:");
        int count = 1;
        for (Transaction transaction : transactions) {
            System.out.println(count + ". " + transaction.getDescription()
                    + " - " + transaction.getDate().format(df));
            count++;
        }
        printLine();
    }

    //@@author
    public void printSavingOverview(FinancialGoal goal) {
        printCenteredTitle("Saving Overview");

        if (goal.isBlank()) {
            printLeftAlignedLine("You haven't set a saving goal yet.");
            printLeftAlignedLine("Tip: Use 'saving > set' to create one and start tracking!");
            printLine();
            return;
        }

        double current = goal.getBalance();
        double target = goal.getTargetAmount();
        double percent = current / target;
        percent = Math.max(0.0, Math.min(percent, 1.0));

        int barLength = 20;
        int filled = (int) (percent * barLength);
        int empty = barLength - filled;

        String bar = "[" + "█".repeat(filled) +
                " ".repeat(empty) +
                "]";

        printLeftAlignedLine("Goal:         \"" + goal.getGoal() + "\"");
        printLeftAlignedLine("Description:  " + goal.getDescription());
        printLeftAlignedLine("");

        printLeftAlignedLine("Status:       You're currently at:" + String.format("  %s  %.1f%% complete",
                bar, percent * 100, current, target));
        if (percent >= 1.0) {
            printLeftAlignedLine("Analysis:     Amazing! You've achieved your savings goal. Time to celebrate!");
        } else if (percent >= 0.75) {
            printLeftAlignedLine("Analysis:     You're almost there! Keep going, you're doing great!");
        } else if (percent >= 0.5) {
            printLeftAlignedLine("Analysis:     You're halfway through. Keep up the good work!");
        } else if (percent > 0.0) {
            printLeftAlignedLine("Analysis:     Good start! Keep saving and you'll get there in no time.");
        } else {
            printLeftAlignedLine("Analysis:     You haven't started saving yet. Let's begin today!");
        }
        printLine();
    }

    public void printCurrencyChoice() {
        ConsoleFormatter.printLine();
        System.out.println("You can enter exit to quit choose progress");
        System.out.println("Please choose a valid currency from the list below:");

        int index = 1;
        for (Currency currency : Currency.values()) {
            System.out.println(index + ". " + currency.name());
            index++;
        }
        ConsoleFormatter.printLine();
    }

    public void printCurrencyHint() {
        System.out.print("Enter currency number (1-" + Currency.values().length + "): ");
    }

    public void printCurrencySetting() {
        System.out.println("Set your default currency successfully!");
        ConsoleFormatter.printLine();
    }

    public void printCategoryChoice() {
        ConsoleFormatter.printLine();
        System.out.println("You can enter exit to quit choose progress");
        System.out.println("Please choose a valid category from the list below:");
        int index = 1;
        for (Category category : Category.values()) {
            System.out.println(index + ". " + category.name());
            index++;
        }
        ConsoleFormatter.printLine();
    }

    public void printCategoryHint() {
        System.out.print("Enter category number (1-" + Category.values().length + "): ");
    }

    public void printCategoryChoose() {
        System.out.println("Choose category successfully!");
        ConsoleFormatter.printLine();
    }
}
