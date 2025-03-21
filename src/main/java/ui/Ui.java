package ui;

import seedu.duke.Transaction;

import java.util.ArrayList;
import java.util.Scanner;

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

    // Lists all upcoming notifications
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

    public void printTransactions(ArrayList<Transaction> transactions) {
        showLine();
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
}
