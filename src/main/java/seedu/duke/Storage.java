package seedu.duke;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.format.DateTimeParseException;

import exceptions.StorageParseException;
import enumstructure.Category;
import enumstructure.Currency;
import enumstructure.Priority;
import enumstructure.Status;
import seedu.duke.budget.Budget;
import seedu.duke.budget.BudgetList;

public class Storage {
    private static final String FOLDER_PATH = "data";
    private static final String FILE_PATH = FOLDER_PATH + "/transactions.csv";
    private static final String GOAL_FILE_PATH = FOLDER_PATH + "/goal.csv";
    private static final String BUDGET_FILE_PATH = FOLDER_PATH + "/budgets.csv";
    private static final String META_FILE_PATH = FOLDER_PATH + "/meta.txt";
    private static final String SETTINGS_FILE_PATH = FOLDER_PATH + "/settings.csv";

    private void createDataFolderIfNeeded() {
        File folder = new File(FOLDER_PATH);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    // ================= TRANSACTIONS =================

    public void saveTransactions(ArrayList<Transaction> transactions) {
        assert transactions != null;
        createDataFolderIfNeeded();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Transaction t : transactions) {
                writer.write(formatTransaction(t));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    public ArrayList<Transaction> loadTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return transactions;
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

    private String formatTransaction(Transaction t) {
        return String.format("%d,%s,%f,%s,%s,%s,%s,%d,%b,%b,%s",
                t.getId(), t.getDescription(), t.getAmount(), t.getCurrency(),
                t.getCategory(), t.getDate(), t.getStatus(),
                t.getRecurringPeriod(), t.isDeleted(), t.isCompleted(), t.getPriority());
    }

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
                currency = Currency.valueOfFullName(parts[3]);
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
            System.out.println(e.getMessage());
            return null;
        }
    }
  

    // 获取当前最大 Transaction ID
    public int loadMaxTransactionId() {
        createDataFolderIfNeeded();
        File file = new File(META_FILE_PATH);
        if (!file.exists()) {
            return 0;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            return line != null ? Integer.parseInt(line.trim()) : 0;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading max transaction ID: " + e.getMessage());
            return 0;
        }
    }

    // 更新最大 Transaction ID
    public void saveMaxTransactionId(int id) {
        createDataFolderIfNeeded();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(META_FILE_PATH))) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            System.out.println("Error saving max transaction ID: " + e.getMessage());
        }
    }

    // ================= FINANCIAL GOAL =================

    public void saveGoal(FinancialGoal goal) {
        createDataFolderIfNeeded();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(GOAL_FILE_PATH))) {
            writer.write(formatGoal(goal));
        } catch (IOException e) {
            System.out.println("Error saving financial goal: " + e.getMessage());
        }
    }

    private String formatGoal(FinancialGoal goal) {
        return String.format("%s,%f,%s,%f,%b",
                goal.getGoal(),
                goal.getTargetAmount(),
                goal.getDescription().replace(",", " "),
                goal.getBalance(),
                goal.isAchieved());
    }

    public FinancialGoal loadGoal() {
        File file = new File(GOAL_FILE_PATH);
        if (!file.exists()) {
            return new FinancialGoal();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                String[] parts = line.split(",", 5);
                String name = parts[0];
                double target = Double.parseDouble(parts[1]);
                String description = parts[2];
                double deposits = Double.parseDouble(parts[3]);
                boolean isAchieved = Boolean.parseBoolean(parts[4]);

                FinancialGoal goal = new FinancialGoal(name, target, description);
                goal.forceSetDeposits(deposits);
                goal.forceSetAchieved(isAchieved);
                return goal;
            }
        } catch (Exception e) {
            System.out.println("Error loading financial goal: " + e.getMessage());
        }

        return new FinancialGoal();
    }

    // ================= BUDGET =================

    public void saveBudgets(BudgetList budgetList) {
        createDataFolderIfNeeded();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BUDGET_FILE_PATH))) {
            for (Budget b : budgetList.getAll()) {
                writer.write(String.format("%s,%f,%f,%s,%s",
                        b.getName(),
                        b.getTotalAmount(),
                        b.getRemainingAmount(),
                        b.getEndDate(),
                        b.getCategory()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving budgets: " + e.getMessage());
        }
    }

    public BudgetList loadBudgets() {
        BudgetList budgetList = new BudgetList();
        File file = new File(BUDGET_FILE_PATH);
        if (!file.exists()) {
            return budgetList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length != 5) {
                    continue;
                }

                String name = parts[0];
                double total = Double.parseDouble(parts[1]);
                double remaining = Double.parseDouble(parts[2]);
                LocalDate endDate = LocalDate.parse(parts[3]);
                Category category = Category.valueOf(parts[4]);

                Budget budget = new Budget(name, total, endDate, category);
                budget.deductAmount(total - remaining);
                budgetList.add(budget);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading budgets: " + e.getMessage());
        }
        return budgetList;
    }

    //Settings

    public void saveDefaultCurrency(Currency currency) {
        createDataFolderIfNeeded();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SETTINGS_FILE_PATH))) {
            writer.write("default_currency=" + currency.name());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving default currency: " + e.getMessage());
        }
    }

    public Currency loadDefaultCurrency() {
        File file = new File(SETTINGS_FILE_PATH);
        if (!file.exists()) {
            return Currency.SGD;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("default_currency=")) {
                    String value = line.substring("default_currency=".length()).trim();
                    return Currency.valueOf(value.toUpperCase());
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error loading default currency: " + e.getMessage());
        }
        return Currency.SGD;
    }

    // load
    public void load(TransactionManager transactions) {
        ArrayList<Transaction> savedTransactions = loadTransactions();
        for (Transaction t : savedTransactions) {
            transactions.addTransaction(t);
        }
        BudgetList loadedBudgets = loadBudgets();
        transactions.setBudgetList(loadedBudgets);
        Currency defaultCurrency = loadDefaultCurrency();
        transactions.setDefaultCurrency(defaultCurrency);
    }

    // Save budget limit
    public void saveBudgetLimit(double limit) {
        try (FileWriter writer = new FileWriter("budget.txt")) {
            writer.write(String.valueOf(limit));
        } catch (IOException e) {
            System.out.println("Failed to save budget limit.");
        }
    }

    // Load budget limit
    public double loadBudgetLimit() {
        File file = new File("budget.txt");
        if (!file.exists()) {
            return -1;
        }

        try (Scanner scanner = new Scanner(file)) {
            return scanner.hasNextDouble() ? scanner.nextDouble() : -1;
        } catch (IOException e) {
            System.out.println("Failed to load budget limit.");
            return -1;
        }
    }
}
