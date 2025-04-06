package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransactionManagerTest {

    private TransactionManager manager;

    @BeforeEach
    public void setUp() {
        manager = new TransactionManager();
    }

    @Test
    public void testAddTransaction() {
        Transaction t1 = new Transaction(1, "Lunch", 12.5,
                Currency.SGD, Category.FOOD, LocalDate.now(), Status.PENDING);
        manager.addTransaction(t1);

        ArrayList<Transaction> transactions = manager.getTransactions();
        assertEquals(1, transactions.size());
        assertEquals("Lunch", transactions.get(0).getDescription());
    }

    @Test
    public void testAddTransactionWithParams() {
        manager.addTransaction(2, "Snacks", 5.0, Category.FOOD,null);
        ArrayList<Transaction> transactions = manager.getTransactions();
        assertEquals(1, transactions.size());
        assertEquals("Snacks", transactions.get(0).getDescription());
    }

    @Test
    public void testTickAndUnTickTransaction() throws Exception {
        Transaction t = new Transaction(3, "Netflix", 15.0, Currency.SGD,
                Category.ENTERTAINMENT, LocalDate.now(), Status.PENDING);
        manager.addTransaction(t);

        manager.tickTransaction(3);
        assertTrue(manager.searchTransaction(3).isCompleted());

        manager.unTickTransaction(3);
        assertFalse(manager.searchTransaction(3).isCompleted());
    }

    @Test
    public void testClearTransactions() {
        manager.addTransaction(4, "Book", 20.0, Category.EDUCATION,null);
        manager.addTransaction(5, "Groceries", 30.0, Category.FOOD,null);
        assertEquals(2, manager.getTransactions().size());

        manager.clear();
        assertEquals(0, manager.getTransactions().size());
    }

    @Test
    public void testDeleteTransaction() {
        Transaction t = new Transaction(6, "Taxi", 10.0, Currency.SGD,
                Category.TRANSPORT, LocalDate.now(), Status.PENDING);
        manager.addTransaction(t);

        assertEquals(1, manager.getTransactions().size());

        manager.deleteExpense(5);

        assertEquals(1, manager.getTransactions().size());

        assertEquals(1, manager.getNum());
    }

}

