package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import ui.Ui;
import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Status;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DukeTest {

    private Duke duke;
    private Ui ui;
    private TransactionManager transactions;
    private FinancialGoal goal;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        // Create real instances of the classes
        ui = new Ui();
        transactions = new TransactionManager();
        goal = new FinancialGoal();

        // Pass the temporary file path to the Storage class to simulate storing transaction data
        // In a real-world scenario, you could inject the file path via a constructor
        storage = new Storage();

        // Initialize Duke
        duke = new Duke();
    }

    @Test
    public void testDukeConstructorInitialization() {
        // Verify that the Duke class is initialized properly
        assertNotNull(duke, "Duke should be initialized");
        assertNotNull(ui, "UI should be initialized");
        assertNotNull(transactions, "TransactionManager should be initialized");
        assertNotNull(goal, "FinancialGoal should be initialized");
        assertNotNull(storage, "Storage should be initialized");
    }

    @Test
    public void testRunWithSavedTransactions() {
        // Create some sample transactions and save them
        ArrayList<Transaction> transactionsList = new ArrayList<>();
        transactionsList.add(new Transaction(1, "Test", 100.0, Currency.SGD, Category.GROCERIES, LocalDate.now(), Status.PENDING));

        // Save the transaction data
        storage.saveTransactions(transactionsList);

        // Reload the transactions and verify they are saved correctly
        ArrayList<Transaction> loadedTransactions = storage.loadTransactions();
        assertFalse(loadedTransactions.isEmpty(), "Transactions should not be empty after saving and loading.");
    }
}

