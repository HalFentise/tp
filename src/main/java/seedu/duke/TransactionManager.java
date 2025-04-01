package seedu.duke;

import java.util.ArrayList;
import java.time.LocalDate;

import constant.Constant;
import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Status;
import exceptions.NullException;
import ui.Ui;

public class TransactionManager {
    private ArrayList<Transaction> transactions;
    private ArrayList<Transaction> upcomingTransactions;
    private int budgetLimit = 0;
    private Currency defaultCurrency = Currency.USD;

    public TransactionManager() {
        transactions = new ArrayList<>();
    }

    public int getNum() {
        return transactions.size();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void addTransaction(int id, String description, int amount, Category category) {
        LocalDate date = LocalDate.now();
        Transaction transaction = new Transaction(id, description, amount, defaultCurrency, category, date, Status.PENDING);
        transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> printTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (!transaction.isDeleted()) {
                printTransactions.add(transaction);
            }
        }
        return printTransactions;
    }

    /**
     * Deletes a transaction from the task list.
     *
     * @param id the index of the task to be removed.
     */
    public void deleteExpense(int id) {
        if (checkIdEmpty(id)) {
            return;
        }
        transactions.remove(id);
    }
    /*
    function to record and trace the total budget limit
     */

    public void checkBudgetLimit() {
        int totalAmount = 0;
        for (Transaction transaction : transactions) {
            if (!transaction.isDeleted()) {
                totalAmount += transaction.getAmount();
            }
        }
        if (totalAmount > budgetLimit) {
            System.out.println("Warning: You have exceeded your budget limit!");
        }
    }

    public Transaction searchTransaction(int id) {
        try {
            for (Transaction transaction : transactions) {
                if (transaction.getId() == id && !transaction.isDeleted()) {
                    return transaction;
                }
            }
            throw new NullException("Transaction is invalid");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public ArrayList<Transaction> searchTransactionList(boolean isIndex, String searchTerm, Ui ui) {
        try {
            ArrayList<Transaction> printTransactions = new ArrayList<>();
            if (isIndex) {
                Transaction transaction = searchTransaction(Integer.parseInt(searchTerm));
                if (transaction != null) {
                    printTransactions.add(transaction);
                }
            } else {
                for (Transaction transaction : transactions) {
                    if (transaction.getDescription().contains(searchTerm)) {
                        printTransactions.add(transaction);
                    }
                }
            }
            if (printTransactions.isEmpty()) {
                throw new NullException("Transaction is invalid");
            }
            return printTransactions;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /*
     * Function for setting the expense limit for a specific time duration?
     * The type default is expense
     * Can keep the amount as for the all-time spend limit first
     */
    public void setBudgetLimit(int amount) {
        try {
            if (amount > 0) {
                budgetLimit = amount;
                //System.out.println("Budget limit set to " + amount + " " + defaultCurrency);
                checkBudgetLimit();  // Check if the new budget limit has been exceeded
            } else {
                throw new NullException("Invalid input amount, amount can not be negative!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Sets a notification for an upcoming transaction
    public void notify(String description, int amount, String categoryString, String date) {
        LocalDate dueDate = LocalDate.parse(date);

        // Add a notification for the specified upcoming expense
        /*String notification = "Reminder: " + description + " of " + amount + " " + defaultCurrency + " in category "
                + categoryString + " is due on " + dueDate;*/

        Category category = Category.valueOf(categoryString);
        Transaction upcomingTransaction = new Transaction(
                transactions.size() + 1,
                description,
                amount,
                defaultCurrency,
                category,
                dueDate,
                Status.PENDING
        );

        upcomingTransactions.add(upcomingTransaction);

        //System.out.println(notification);
    }

    // Lists all upcoming notifications
    public void listNotifications(String description) {
        if (upcomingTransactions.isEmpty()) {
            System.out.println("No upcoming expenses.");
        } else {
            System.out.println("Upcoming Expenses:");
            for (Transaction transaction : upcomingTransactions) {
                if (transaction.getDescription().equals(description)) {
                    System.out.println("- " + transaction.getDescription() + " of " + transaction.getAmount() + " "
                            + transaction.getCurrency() + " in category " + transaction.getCategory() + " is due on "
                            + transaction.getDate().toString());
                }
            }
        }
    }

    public void addTag(int id, String tag) {
        Transaction transaction = searchTransaction(id);
        if (transaction == null) {
            return;
        }
        transaction.addTag(tag);
    }

    public void removeTag(int id, String tag) {
        Transaction transaction = searchTransaction(id);
        if (transaction == null) {
            return;
        }
        transaction.removeTag(tag);
    }

    public void tickTransaction(int id) {
        Transaction transaction = searchTransaction(id);
        if (transaction == null) {
            return;
        }
        transaction.complete();
    }

    public void unTickTransaction(int id) {
        Transaction transaction = searchTransaction(id);
        if (transaction == null) {
            return;
        }
        transaction.notComplete();
    }

    public void setRecur(int id, int recurringPeriod) {
        Transaction transaction = searchTransaction(id);
        if (transaction == null) {
            return;
        }
        transaction.setRecurringPeriod(recurringPeriod);
    }

    public void editDescription(int id, String newDescription) {
        if (checkIdEmpty(id)) {
            return;
        }
        transactions.get(id).setDescription(newDescription);
    }

    public void editCategory(int id, String newCategory) {
        if (checkIdEmpty(id)) {
            return;
        }
        transactions.get(id).setCategory(Category.valueOf(newCategory));
    }

    public void editAmount(int id, int newAmount) {
        if (checkIdEmpty(id)) {
            return;
        }
        transactions.get(id).setAmount(newAmount);
    }

    public void editCurrency(int id, String newCurrency) {
        if (checkIdEmpty(id)) {
            return;
        }
        transactions.get(id).setCurrency(Currency.valueOf(newCurrency));
    }

    public boolean checkIdEmpty(int id) {
        if (transactions.get(id) == null) {
            System.out.println(Constant.INVALID_TRANSACTION_ID);
            return true;
        }
        return false;
    }
}

