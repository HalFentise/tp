package parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.FinancialGoal;
import seedu.duke.Storage;
import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import ui.Ui;
import enumStructure.Category;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private Ui ui;
    private TransactionManager transactions;
    private FinancialGoal goal;
    private Storage storage;

    @BeforeEach
    void setUp() {
        ui = new Ui();
        transactions = new TransactionManager();
        goal = new FinancialGoal();
        storage = new Storage();
    }

    @Test
    void testHelpCommand() {
        // Simulate help command
        String userInput = "help";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            // Add assertions based on expected output from help() method
            // For simplicity, we assume the help method prints a message, so we would check if it doesn't throw errors
        } catch (Exception e) {
            fail("Help command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testAddCommandValid() {
        // Simulate add command with valid input
        String userInput = "add d/Transaction description a/100 c/food";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            assertEquals(1, transactions.getNum());  // Assuming the first transaction is added
            Transaction addedTransaction = transactions.getTransactions().get(0);
            assertEquals("Transaction description", addedTransaction.getDescription());
            assertEquals(100, addedTransaction.getAmount());
            assertEquals(Category.FOOD, addedTransaction.getCategory());
        } catch (Exception e) {
            fail("Add command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testAddCommandInvalid() {
        // Simulate add command with missing fields
        String userInput = "add d/Transaction description a/100";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            fail("Invalid add command should have thrown an error");
        } catch (Exception e) {
            assertEquals("No category found", e.getMessage());
        }
    }

    @Test
    void testListCommand() {
        // Simulate list command
        String userInput = "list";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            // Check that the list of transactions is printed without errors
            // This will depend on the behavior of `ui.printTransactions`
        } catch (Exception e) {
            fail("List command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testSearchCommand() {
        // Simulate search command
        String userInput = "search id-1";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            // Add a transaction to search for
            transactions.addTransaction(1, "Test Transaction", 100, Category.FOOD);
            // Assuming that searchTransactionList correctly finds the transaction by id
            assertEquals(1, transactions.searchTransactionList(true, "1").size());
        } catch (Exception e) {
            fail("Search command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testTickCommand() {
        // Simulate tick command
        String userInput = "tick 1";
        try {
            transactions.addTransaction(1, "Test Transaction", 100, Category.FOOD);
            Parser.parser(userInput, ui, transactions, goal, storage);
            Transaction transaction = transactions.searchTransaction(1);
            assertTrue(transaction.isCompleted());
        } catch (Exception e) {
            fail("Tick command failed with error: " + e.getMessage());
        }
    }

    @Test
    void testInvalidCommand() {
        // Simulate an invalid command
        String userInput = "invalidCommand";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            fail("Invalid command should have thrown an error");
        } catch (Exception e) {
            assertEquals("Invalid input", e.getMessage());
        }
    }

    @Test
    void testExitCommand() {
        // Simulate exit command
        String userInput = "exit";
        try {
            Parser.parser(userInput, ui, transactions, goal, storage);
            // Check if the system exits correctly (this might need some additional logic for testing)
        } catch (Exception e) {
            fail("Exit command failed with error: " + e.getMessage());
        }
    }
}
