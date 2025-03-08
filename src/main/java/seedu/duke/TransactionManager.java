package seedu.duke;

import java.util.ArrayList;
import java.time.LocalDate;
import exceptions.NullException;

public class TransactionManager {
    private ArrayList<Transaction> transactions;
    private Currency defaultCurrency = Currency.USD;
    private Category defaultCategory = Category.OTHER;

    public TransactionManager() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void addTransaction(int id, int amount) {
        LocalDate date = LocalDate.now();
        Transaction transaction = new Transaction(id,amount,defaultCurrency,date, Status.PENDING);
        transactions.add(transaction);
    }

    public ArrayList<Transaction> getTransactions() {
        ArrayList<Transaction> printTransactions = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (!transaction.isDeleted()){
                printTransactions.add(transaction);
            }
        }
        return printTransactions;
    }

    public Transaction searchTransaction(int id) {
        try {
            for (Transaction transaction : transactions) {
                if (transaction.getId() == id && !transaction.isDeleted()) {
                    return transaction;
                }
            }
            throw new NullException("seedu.duke.Transaction not found");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
