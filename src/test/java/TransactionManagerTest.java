import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.duke.Transaction;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionManagerTest {
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        Transaction e = new Transaction(1, "Dinner", 100.0, Currency.USD, Category.FOOD, LocalDate.of(2025, 3, 28), Status.PENDING);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals(1, transaction.getId());
        assertEquals("Lunch", transaction.getDescription());
        assertEquals(1000, transaction.getAmount());
        assertEquals(Currency.USD, transaction.getCurrency());
        assertEquals(Category.FOOD, transaction.getCategory());
        assertEquals(LocalDate.of(2025, 3, 28), transaction.getDate());
        assertEquals(Status.PAID, transaction.getStatus());
        assertFalse(transaction.isDeleted());
        assertFalse(transaction.isCompleted());
        assertEquals(0, transaction.getRecurringPeriod());
        assertTrue(transaction.getTags().isEmpty());
    }

    @Test
    public void testSettersAndUpdateMethods() {
        transaction.setDescription("Brunch");
        transaction.setCategory(Category.ENTERTAINMENT);
        transaction.setDate(LocalDate.of(2025, 3, 30));

        assertEquals("Brunch", transaction.getDescription());
        assertEquals(Category.ENTERTAINMENT, transaction.getCategory());
        assertEquals(LocalDate.of(2025, 3, 30), transaction.getDate());
    }

    @Test
    public void testCompleteAndNotComplete() {
        transaction.complete();
        assertTrue(transaction.isCompleted());

        transaction.notComplete();
        assertFalse(transaction.isCompleted());
    }

    @Test
    public void testAddRemoveTag() {
        transaction.addTag("important");
        transaction.addTag("food");

        assertTrue(transaction.containsTag("important"));
        assertTrue(transaction.containsTag("food"));

        transaction.removeTag("important");
        assertFalse(transaction.containsTag("important"));

        ArrayList<String> tags = transaction.getTags();
        assertEquals(1, tags.size());
        assertEquals("food", tags.get(0));
    }

    @Test
    public void testDeleteAndRecover() {
        transaction.delete();
        assertTrue(transaction.isDeleted());

        transaction.recover();
        assertFalse(transaction.isDeleted());
    }

    @Test
    public void testRecurringPeriod() {
        transaction.setRecurringPeriod(30);
        assertEquals(30, transaction.getRecurringPeriod());
    }

    @Test
    public void testIsSameTransaction() {
        Transaction same = new Transaction(1, "Dinner", 500, Currency.SGD, LocalDate.now(), Status.PENDING);
        Transaction different = new Transaction(2, "Dinner", 500, Currency.SGD, LocalDate.now(), Status.PENDING);

        assertTrue(transaction.isSameTransaction(same));
        assertFalse(transaction.isSameTransaction(different));
    }

    @Test
    public void testToStringIncludesAllFields() {
        transaction.setRecurringPeriod(7);
        String result = transaction.toString();
        assertTrue(result.contains("Transaction id: 1"));
        assertTrue(result.contains("amount: 1000"));
        assertTrue(result.contains("description: Lunch"));
        assertTrue(result.contains("category: FOOD"));
        assertTrue(result.contains("period: 7"));
    }
}
