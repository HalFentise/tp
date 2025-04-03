package seedu.duke;

import java.util.ArrayList;
import java.time.LocalDate;

import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Status;
import exceptions.NullException;
import ui.Ui;

public class TransactionManager {
    private ArrayList<Transaction> transactions;
    private ArrayList<Transaction> upcomingTransactions;
    private Currency defaultCurrency = Currency.SGD;

    public TransactionManager() {
        transactions = new ArrayList<>();
        upcomingTransactions = new ArrayList<>();
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

    public void addTransaction(int id, String description, int amount, Category category, LocalDate date) {
        Transaction transaction = new Transaction(id, description, amount, defaultCurrency, category, date, Status.PENDING);
        transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> printTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (!transaction.isDeleted()) {
                printTransactions.add(transaction);
                sortTransactions(printTransactions);
            }
        }
        return printTransactions;
    }

    /**
     * Deletes a translation from the task list.
     *
     * @param index the index of the task to be removed.
     */
    public void deleteExpense(int index) {
        transactions.remove(index);
    }

    /*
    function to record and trace the total budget limit
     */
    public void checkBudgetLimit(int budgetLimit) {
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

    // Sets a notification for an upcoming transaction
    public void notify(String description, int amount, String categoryString, String date) {
        LocalDate dueDate = LocalDate.parse(date);

        Category category = Category.valueOf(categoryString);

        for (Transaction transaction : transactions) {
            if (transaction.getDescription().equals(description) && transaction.getCategory().equals(category)) {
                transaction.setDate(dueDate);
            }
        }
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

    public ArrayList<Transaction> sortTransactions(ArrayList<Transaction> transactions) {

        transactions.sort((t1, t2) -> {
            if (t1.getDate() == null && t2.getDate() == null) {
                return 0;
            }
            if (t1.getDate() == null) {
                return -1;
            }
            if (t2.getDate() == null) {
                return 1;
            }
            return t1.getDate().compareTo(t2.getDate());
        });

        return transactions;
    }

    public ArrayList<Transaction> getTransactionsOnDate(LocalDate targetDate) {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction t : getTransactions()) {
            if (t.getDate() != null && t.getDate().equals(targetDate)) {
                filteredTransactions.add(t);
            }
        }
        return filteredTransactions;
    }

    public ArrayList<Transaction> getTransactionsThisMonth() {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction t : getTransactions()) {
            if (t.getDate() != null &&
                    t.getDate().getMonthValue() == month &&
                    t.getDate().getYear() == year) {
                filteredTransactions.add(t);
            }
        }
        return filteredTransactions;
    }

    public ArrayList<Transaction> getTransactionsThisWeek() {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        ArrayList<Transaction> filteredTransactions = new ArrayList<>();
        for (Transaction t : getTransactions()) {
            if (t.getDate() != null &&
                    !t.getDate().isBefore(today) &&
                    !t.getDate().isAfter(nextWeek)) {
                filteredTransactions.add(t);
            }
        }
        return filteredTransactions;
    }

    public void getUpcomingTransactions(String period) {

        period = period.toLowerCase();
        switch (period) {
            case "today":
                System.out.println(getTransactionsOnDate(LocalDate.now()));
            case "week":
                System.out.println(getTransactionsThisWeek());
            case "month":
                System.out.println(getTransactionsThisMonth());
            default:
                try {
                    LocalDate date = LocalDate.parse(period);
                    System.out.println(getTransactionsOnDate(date));
                } catch (Exception e) {
                    System.out.println("Invalid period. Use 'today', 'week', 'month', or a date (yyyy-mm-dd)");
                }
        }
    }
}


