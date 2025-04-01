package ui;

import seedu.duke.FinancialGoal;
import seedu.duke.Transaction;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

import static constant.Constant.*;

public class Ui {
    /**
     * Scanner for reading user input.
     */
    private final Scanner scanner;

    /**
     * Constructs a {@code Ui} instance and initializes the input scanner.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads the user input from the command line.
     *
     * @return the user's input as a string.
     */
    public String readCommand() {
        System.out.println("Enter your command:");
        String input = scanner.nextLine();
        return input;
    }

    /**
     * Prints the welcome message when the application starts.
     */
    public void printWelcomeMessage() {
        showLine();
        System.out.println(" Hello! This is NoteUrSavings here!" + "\n" + " What can I do for you?");
        showLine();
    }

    public void printExit() {
        showLine();
        System.out.println("Goodbye! Hope to see you again!");
    }

    /**
     * Prints a given message to the console.
     *
     * @param message the message to be displayed.
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints a horizontal line separator.
     */
    public static void showLine() {
        System.out.println(LINE_SEPARATOR);
    }

    /**
     * Displays an error message.
     *
     * @param message the error message to be displayed.
     */
    public void showError(String message) {
        showLine();
        System.out.println("Error: " + message); // Display the error
        showLine();
    }

    /*
     * Function for setting the expense limit for a specific time duration?
     * The type default is expense
     * Can keep the amount as for the all-time spend limit first
     */

    /**
     * Prints a message indicating that a task has been deleted.
     *
     * @param transaction the task that was deleted.
     * @param Count       the new total number of tasks in the list.
     */
    public static void printDeleteTask(Transaction transaction, int Count) {
        showLine();
        System.out.printf(INDENT + "Noted. I've removed this transaction:\n", "");
        System.out.printf(INDENT + "%s\n", "", transaction.toString());
        System.out.printf(INDENT + "Now you have %d transactions in the list.%n", "", Count);
        showLine();
    }

    public void PrintBudgetLimit(ArrayList<Transaction> transaction, int amount) {
        showLine();
        if (transaction.isEmpty()) {
            System.out.println("Please add a translation first before you set the budget!");
        } else {
            System.out.println("Budget limit set to " + amount + " " + transaction.get(0).getCurrency());
        }
        showLine();
    }

    // Lists current specific upcoming notification
    public void listNotification(ArrayList<Transaction> upcomingTransactions, String description) {
        showLine();
        if (upcomingTransactions.isEmpty()) {
            System.out.println("No upcoming expenses.");
        } else {
            System.out.println("Upcoming Expenses:");
            for (Transaction transaction : upcomingTransactions) {
                if (transaction.getDescription().equals(description) && transaction.getDate() != null) {
                    System.out.println("- " + transaction.getDescription() + " of " + transaction.getAmount() + " "
                            + transaction.getCurrency() + " in category " + transaction.getCategory() + " is due on "
                            + transaction.getDate().toString());
                }
            }
        }
        showLine();
    }

    // Lists all upcoming notifications
    public void listNotifications(ArrayList<Transaction> upcomingTransactions) {
        if (upcomingTransactions.isEmpty()) {
            System.out.println("No upcoming expenses.");
        } else {
            System.out.println("Upcoming Expenses:");
            for (Transaction transaction : upcomingTransactions) {
                if (transaction.getDate() != null) {
                    System.out.println("- " + transaction.getDescription() + " of " + transaction.getAmount() + " "
                            + transaction.getCurrency() + " in category " + transaction.getCategory() + " is due on "
                            + transaction.getDate().toString());
                }
            }
        }
    }

    //
    public void PrintPriority(ArrayList<Transaction> transactions, int index) {
        showLine();
        if (transactions.isEmpty()) {
            System.out.println("Please add a transaction first before you set the priority!");
        } else {
            System.out.println("Priority is set to " + transactions.get(index).getPriority() + " for current transaction.");
        }
        showLine();
    }

    // Lists all transactions that have the high priority
    public void listPriorities(ArrayList<Transaction> upcomingTransactions) {
        String defaultPriority = "HIGH";
        boolean hasHighPriority = false;

        for (Transaction transaction : upcomingTransactions) {
            if (transaction.getPriority() != null && transaction.getPriority().toString().equalsIgnoreCase(defaultPriority)) {
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

    public void printTransactions(ArrayList<Transaction> transactions) {
        showLine();
        if (transactions.isEmpty()) {
            System.out.println("No transaction found.");
            showLine();
            return;
        }
        System.out.println("Here is the list of transactions:");
        for (Transaction transaction : transactions) {
            printTransaction(transaction);
            System.out.println(" ");
        }
        showLine();
    }

    public void printTransaction(Transaction transaction) {
        System.out.println(transaction);
    }

    public void tickTransaction(Transaction transaction) {
        showLine();
        System.out.println("I have ticked the following transaction:");
        printTransaction(transaction);
        showLine();
    }

    public void unTickTransaction(Transaction transaction) {
        showLine();
        System.out.println("I untick the following transaction:");
        printTransaction(transaction);
        showLine();
    }

    public void add(Transaction transaction) {
        showLine();
        System.out.println("I have added the following transactions:");
        printTransaction(transaction);
        showLine();
    }

    public void search(boolean isIndex) {
        if (isIndex) {
            System.out.println("I have searched the transaction with the given index.");
        } else {
            System.out.println("I have searched the transactions containing the keywords.");
        }
    }

    public void setPeriod(Transaction transaction, int recurringPeriod) {
        showLine();
        if (recurringPeriod > 0) {
            System.out.println("I have set the given transaction to recur every"
                    + (recurringPeriod == 1 ? "day." : " " + recurringPeriod + " days."));
        } else {
            System.out.println("I have disabled this transaction from recurring.");
        }
        printTransaction(transaction);
        showLine();
    }

    public void printGoal(FinancialGoal goal) {
        showLine();
        System.out.println(goal);
        showLine();
    }

    public void setGoalTarget(FinancialGoal goal) {
        showLine();
        System.out.println("I have updated your target to: " + goal.getTargetAmount());
        showLine();
    }

    public void setGoalDescription(FinancialGoal goal) {
        showLine();
        System.out.println("I have updated your description to:\n" + goal.getDescription());
        showLine();
    }

    public void setGoalTitle(FinancialGoal goal) {
        showLine();
        System.out.println("I have updated your goal to:\n" + goal.getGoal());
        showLine();
    }

    public static void createGoalConfirm() {
        showLine();
        System.out.println("Want to set a new goal (Y/N)? ");
        showLine();
    }

    public static void createGoalName() {
        System.out.println("Name of new goal:");
        showLine();
    }

    public static void createGoalTarget() {
        System.out.println("Target amount of new goal:");
        showLine();
    }

    public static void createGoalDescription() {
        showLine();
        System.out.println("Description of new goal:");
        showLine();
    }

    public static void createGoalSuccess() {
        showLine();
        System.out.println("Goal successfully created\nRun \'goal\' to see it!");
        showLine();
    }

    public static void createGoalAborted() {
        showLine();
        System.out.println("Goal creation cancelled by user.");
        showLine();
    }

    public static void addToSavings() {

    }

    public static void subFromSavings(int amount, int currentAmount) {
        showLine();
        System.out.println("Subtracted " + amount + " from your savings.");
        if (currentAmount < 0) {
            System.out.println("Warning. You currently have a negative balance.");
        }
        showLine();
    }

    public static boolean printGoalStatus(int currentAmount, int targetAmount) {
        showLine();
        if (currentAmount >= targetAmount) {
            System.out.println("You have achieved the goal! Congratulations!");
            return true;
        }
        System.out.println("You're " + currentAmount + " out of " + targetAmount + ". Good luck!");
        showLine();
        return false;
    }

    public void printEdited(String value, int typeId) {
        String type = "";
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
        }

        showLine();
        System.out.println("Done! The " + type
                + " of the target transaction has been updated to:\n" + value);
        showLine();
    }

    public static void printRecurringTransactions(ArrayList<Transaction> transactions) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("E, dd MMM yyyy");
        showLine();
        System.out.println("Here is a list of your upcoming recurring payments:");
        int count = 1;
        for (Transaction transaction : transactions) {
            System.out.println(count + ". " + transaction.getDescription()
                    + " - " + transaction.getDate().format(df));
            count++;
        }
        showLine();
    }
}
