package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.List;
import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Status;

class TransactionManagerTest {

    private TransactionManager manager;

    @BeforeEach
    void setUp() {
        manager = new TransactionManager();
    }

    @Test
    void testAddTransaction() {
        Transaction transaction = new Transaction(1, "Groceries", 200,
                Currency.USD, Category.FOOD, LocalDate.of(2025, 3, 10), Status.PENDING);
        manager.addTransaction(transaction);

        List<Transaction> transactions = manager.getTransactions();
        assertEquals(1, transactions.size());
        assertEquals("Groceries", transactions.get(0).getDescription());
    }

    @Test
    void testDeleteTransaction() {
        Transaction transaction = new Transaction(1, "Coffee", 5,
                Currency.USD, Category.EDUCATION, LocalDate.of(2025, 3, 11), Status.PENDING);
        manager.addTransaction(transaction);
        manager.deleteExpense(0);

        List<Transaction> transactions = manager.getTransactions();
        assertEquals(0, transactions.size());
    }
}
