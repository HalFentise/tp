package seedu.duke;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Priority;
import enumStructure.Status;

public class Storage {
    private static final String FOLDER_PATH = "data";  // Folder path
    private static final String FILE_PATH = FOLDER_PATH + "/transactions.csv";  // File path

    // Check and create the folder if it doesn't exist
    private void createDataFolderIfNeeded() {
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdirs();  // Create the folder
        }
    }

    // Save the transaction data to a CSV file
    public void saveTransactions(ArrayList<Transaction> transactions) {
        assert transactions != null : "Transaction list should not be null";

        createDataFolderIfNeeded();  // Ensure the folder exists

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Transaction t : transactions) {
                assert t != null : "Transaction object should not be null";
                writer.write(formatTransaction(t));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    // Load transaction data from the CSV file
    public ArrayList<Transaction> loadTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return transactions; // Return an empty list if the file doesn't exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                assert !line.trim().isEmpty() : "CSV line should not be empty";
                Transaction t = parseTransaction(line);
                if (t != null) {
                    transactions.add(t);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }

    // Convert a Transaction object into a CSV format string
    private String formatTransaction(Transaction t) {
        assert t != null : "Transaction cannot be null";
        assert t.getCurrency() != null : "Currency cannot be null";
        assert t.getCategory() != null : "Category cannot be null";
        assert t.getDate() != null : "Date cannot be null";
        assert t.getStatus() != null : "Status cannot be null";
        assert t.getPriority() != null : "Priority cannot be null";
        assert t.getDescription() != null : "Description cannot be null";

        return String.format("%d,%s,%f,%s,%s,%s,%s,%d,%b,%b,%s",
                t.getId(), t.getDescription(), t.getAmount(), t.getCurrency(),
                t.getCategory(), t.getDate(), t.getStatus(),
                t.getRecurringPeriod(), t.isDeleted(), t.isCompleted(), t.getPriority());
    }

    // Parse a CSV line into a Transaction object
    private Transaction parseTransaction(String line) {
        try {
            assert line != null : "CSV line should not be null";
            String[] parts = line.split(",");
            assert parts.length == 11 : "Expected 11 fields but got " + parts.length;

            int id = Integer.parseInt(parts[0]);
            String description = parts[1];
            double amount = Double.parseDouble(parts[2]);
            Currency currency = Currency.valueOf(parts[3]);
            Category category = Category.valueOf(parts[4]);
            LocalDate date = LocalDate.parse(parts[5]);
            Status status = Status.valueOf(parts[6].toUpperCase());
            int recurringPeriod = Integer.parseInt(parts[7]);
            boolean isDeleted = Boolean.parseBoolean(parts[8]);
            boolean isCompleted = Boolean.parseBoolean(parts[9]);
            Priority priority = Priority.valueOf(parts[10].toUpperCase());

            assert amount >= 0 : "Amount should be non-negative";
            assert currency != null : "Currency should not be null";
            assert category != null : "Category should not be null";
            assert date != null : "Date should not be null";
            assert status != null : "Status should not be null";

            Transaction transaction = new Transaction(id, description, amount, currency, category, date, status);
            transaction.setRecurringPeriod(recurringPeriod);
            transaction.setPriority(priority);

            if (isDeleted) {
                transaction.delete();
            }
            if (isCompleted) {
                transaction.complete();
            }
            return transaction;
        } catch (Exception e) {
            System.out.println("Error parsing transaction: " + line + " - " + e.getMessage());
            return null;
        }
    }
}



