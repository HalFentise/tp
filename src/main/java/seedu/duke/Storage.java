package seedu.duke;

import java.io.File;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.format.DateTimeParseException;

import exceptions.StorageParseException;
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
            if (line == null || line.trim().isEmpty()) {
                throw new StorageParseException("Storage is empty.");
            }

            String[] parts = line.split(",");
            if (parts.length != 11) {
                throw new StorageParseException("Storage format error.");
            }

            int id;
            double amount;
            int recurringPeriod;
            boolean isDeleted;
            boolean isCompleted;

            try {
                id = Integer.parseInt(parts[0]);
            } catch (NumberFormatException e) {
                throw new StorageParseException("Invalid ID format: " + parts[0]);
            }

            String description = parts[1];

            try {
                amount = Double.parseDouble(parts[2]);
            } catch (NumberFormatException e) {
                throw new StorageParseException("Invalid amount format: " + parts[2]);
            }

            Currency currency;
            try {
                currency = Currency.valueOf(parts[3]);
            } catch (IllegalArgumentException e) {
                throw new StorageParseException("Invalid currency type: " + parts[3]);
            }

            Category category;
            try {
                category = Category.valueOf(parts[4]);
            } catch (IllegalArgumentException e) {
                throw new StorageParseException("Invalid category type: " + parts[4]);
            }

            LocalDate date;
            try {
                date = LocalDate.parse(parts[5]);
            } catch (DateTimeParseException e) {
                throw new StorageParseException("Invalid date format: " + parts[5]);
            }

            Status status;
            try {
                status = Status.valueOf(parts[6].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new StorageParseException("Invalid status value: " + parts[6]);
            }

            try {
                recurringPeriod = Integer.parseInt(parts[7]);
            } catch (NumberFormatException e) {
                throw new StorageParseException("Invalid recurring period: " + parts[7]);
            }

            try {
                isDeleted = Boolean.parseBoolean(parts[8]);
                isCompleted = Boolean.parseBoolean(parts[9]);
            } catch (Exception e) {
                throw new StorageParseException("Invalid boolean value in deleted/completed status.");
            }

            Priority priority;
            try {
                priority = Priority.valueOf(parts[10].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new StorageParseException("Invalid priority: " + parts[10]);
            }

            // Build transaction
            Transaction transaction = new Transaction(id, description, amount, currency, category, date, status);
            transaction.setRecurringPeriod(recurringPeriod);
            transaction.setPriority(priority);

            if (isDeleted) transaction.delete();
            if (isCompleted) transaction.complete();

            return transaction;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
