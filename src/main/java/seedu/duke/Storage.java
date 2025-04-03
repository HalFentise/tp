package seedu.duke;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Priority;
import enumStructure.Status;

import javax.swing.*;

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
        createDataFolderIfNeeded();  // Ensure the folder exists

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Transaction t : transactions) {
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
        return String.format("%d,%s,%d,%s,%s,%s,%s,%d,%b,%b,%s",
                t.getId(), t.getDescription(), t.getAmount(), t.getCurrency(),
                t.getCategory(), t.getDate(), t.getStatus(),
                t.getRecurringPeriod(), t.isDeleted(), t.isCompleted(), t.getPriority());
    }

    // Parse a CSV line into a Transaction object
    private Transaction parseTransaction(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length != 11) {
                throw new IllegalArgumentException("Invalid number of fields in transaction: " + line);
            }

            int id = Integer.parseInt(parts[0]);
            String description = parts[1];
            int amount = Integer.parseInt(parts[2]);
            Currency currency = Currency.valueOf(parts[3]);
            Category category = Category.valueOf(parts[4]);
            LocalDate date = LocalDate.parse(parts[5]);
            Status status = Status.valueOf(parts[6].toUpperCase());
            int recurringPeriod = Integer.parseInt(parts[7]);
            boolean isDeleted = Boolean.parseBoolean(parts[8]);
            boolean isCompleted = Boolean.parseBoolean(parts[9]);
            Priority priority = Priority.valueOf(parts[10].toUpperCase());

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


