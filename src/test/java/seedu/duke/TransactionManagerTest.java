package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import enums.Category;
import enums.Currency;
import enums.Status;
import exceptions.InvalidCommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionManagerTest {

    private TransactionManager manager;

    private Storage mockStorage;

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
        manager.addTransaction("Snacks", 5.0, Category.FOOD, null);
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
        manager.addTransaction("Book", 20.0, Category.EDUCATION, null);
        manager.addTransaction("Groceries", 30.0, Category.FOOD, null);
        assertEquals(2, manager.getTransactions().size());
    }

    @Test
    public void testDeleteTransaction() {
        Transaction t = new Transaction(6, "Taxi", 10.0, Currency.SGD,
                Category.TRANSPORT, LocalDate.now(), Status.PENDING);
        manager.addTransaction(t);

        assertEquals(1, manager.getTransactions().size());

        manager.deleteExpense(5);

        assertEquals(1, manager.getTransactions().size());

        assertEquals(6, manager.getNum());
    }


    //@@author Lukapeng77
    @Test
    void testSetAndGetBudgetLimit() {
        manager.setBudgetLimit(800.5);
        assertEquals(800.5, manager.getBudgetLimit());

        manager.setBudgetLimit(123.5);
        assertEquals(123.5, manager.getBudgetLimit());
    }

    @Test
    void testLoadBudgetFromStorage() {
        manager.loadBudgetFromStorage();

        assertEquals(0.0, manager.getBudgetLimit());
        assertFalse(manager.getBudgetLimit() > 0);
    }

    @Test
    void testGetTotalTransactionAmount() {
        manager.addTransaction("Groceries", 120, Category.FOOD, LocalDate.now());
        manager.addTransaction("Taxi", 80, Category.TRANSPORT, LocalDate.now());

        double total = manager.getTotalTransactionAmount();
        assertEquals(200.0, total);
    }

    @Test
    void testGetTransactionsBetween() {
        LocalDate start = LocalDate.of(2024, 1, 1);
        LocalDate mid = LocalDate.of(2024, 2, 1);
        LocalDate end = LocalDate.of(2024, 3, 1);

        manager.addTransaction("T1", 100, Category.FOOD, start);
        manager.addTransaction("T2", 200, Category.FOOD, mid);
        manager.addTransaction("T3", 300, Category.FOOD, end);

        ArrayList<Transaction> result = manager.getTransactionsBetween(start, end);
        assertEquals(3, result.size());
    }

//    @Test
//    void testCheckBudgetLimit() {
//        manager.addTransaction("Lunch", 50, Category.FOOD, LocalDate.now());
//        manager.addTransaction("Books", 100, Category.EDUCATION, LocalDate.now());
//
//        manager.checkBudgetLimit();
//
//        assertEquals(200, manager.getBudgetLimit());
//        assertEquals(150, manager.getTotalTransactionAmount());
//        assertEquals(50, manager.getBudgetLimit() - manager.getTotalTransactionAmount());
//    }

    @Test
    void testNotify_SuccessfulUpdate() {
        Transaction transaction = new Transaction(1, "Gym membership", 75, Currency.SGD, Category.HEALTHCARE,
                LocalDate.of(2024, 1, 15), Status.PENDING);
        manager.addTransaction(transaction);

        manager.notify("Gym membership", "HEALTHCARE", LocalDate.of(2024, 2, 15));

        assertEquals(LocalDate.of(2024, 2, 15), manager.searchTransaction(1).getDate());
    }

    @Test
    public void testSearchTransactionListByIndex() throws Exception {
        Transaction t1 = new Transaction(1, "Lunch", 10.0, Currency.SGD, Category.FOOD,
                LocalDate.now(), Status.PENDING);
        manager.addTransaction(t1);

        ArrayList<Transaction> result = manager.searchTransactionList(true, "1");
        assertEquals(1, result.size());
        assertEquals("Lunch", result.get(0).getDescription());
    }

    @Test
    public void testSearchTransactionListByKeyword() throws Exception {
        Transaction t1 = new Transaction(2, "Coffee", 5.0, Currency.SGD, Category.FOOD,
                LocalDate.now(), Status.PENDING);
        Transaction t2 = new Transaction(3, "Groceries", 20.0, Currency.SGD, Category.FOOD,
                LocalDate.now(), Status.PENDING);
        manager.addTransaction(t1);
        manager.addTransaction(t2);

        ArrayList<Transaction> result = manager.searchTransactionList(false, "Coffee");
        assertEquals(1, result.size());
        assertEquals("Coffee", result.get(0).getDescription());
    }

    @Test
    public void testSearchTransactionListInvalidId() {
        assertThrows(InvalidCommand.class, () -> {
            manager.searchTransactionList(true, "notANumber");
        });
    }

    @Test
    public void testGetRecurringTransactions() {
        Transaction t1 = new Transaction(4, "Subscription", 10.0, Currency.SGD, Category.ENTERTAINMENT,
                LocalDate.now(), Status.PENDING);
        t1.setRecurringPeriod(30);

        Transaction t2 = new Transaction(5, "One-time", 20.0, Currency.SGD, Category.OTHER,
                LocalDate.now(), Status.PENDING);

        manager.addTransaction(t1);
        manager.addTransaction(t2);

        ArrayList<Transaction> recurring = manager.getRecurringTransactions();
        assertEquals(1, recurring.size());
        assertEquals("Subscription", recurring.get(0).getDescription());
    }

    @Test
    public void testSortRecurringTransactions() {
        Transaction t1 = new Transaction(6, "Old Sub", 15.0, Currency.SGD, Category.ENTERTAINMENT,
                LocalDate.now().minusDays(60), Status.PENDING);
        t1.setRecurringPeriod(30);

        Transaction t2 = new Transaction(7, "Recent Sub", 15.0, Currency.SGD, Category.ENTERTAINMENT,
                LocalDate.now().minusDays(10), Status.PENDING);
        t2.setRecurringPeriod(5);

        ArrayList<Transaction> list = new ArrayList<>();
        list.add(t1);
        list.add(t2);

        ArrayList<Transaction> sorted = manager.sortRecurringTransactions(list);

        assertFalse(sorted.get(1).getDate().isBefore(sorted.get(0).getDate()));
        assertTrue(sorted.get(0).getDate().isAfter(LocalDate.now().minusDays(1)));
    }

    @Test
    public void testSetRecurValid() throws Exception {
        Transaction t1 = new Transaction(8, "Gym", 50.0, Currency.SGD, Category.HEALTH,
                LocalDate.now(), Status.PENDING);
        manager.addTransaction(t1);

        manager.setRecur(8, 7);
        assertEquals(7, t1.getRecurringPeriod());
    }

    @Test
    public void testSetRecurInvalid() {
        assertThrows(Exception.class, () -> {
            manager.setRecur(999, 5);
        });
    }

    @Test
    public void testEditDescription() throws Exception {
        Transaction t = new Transaction(9, "Old", 100.0, Currency.SGD, Category.OTHER,
                LocalDate.now(), Status.PENDING);
        manager.addTransaction(t);

        manager.editInfo(9, "New Desc", 0);
        assertEquals("New Desc", t.getDescription());
    }

    @Test
    public void testEditCategory() throws Exception {
        Transaction t = new Transaction(10, "Stuff", 20.0, Currency.SGD, Category.FOOD,
                LocalDate.now(), Status.PENDING);
        manager.addTransaction(t);

        manager.editInfo(10, "ENTERTAINMENT", 1);
        assertEquals(Category.ENTERTAINMENT, t.getCategory());
    }

    @Test
    public void testEditAmount() throws Exception {
        Transaction t = new Transaction(11, "Taxi", 15.0, Currency.SGD, Category.TRANSPORT,
                LocalDate.now(), Status.PENDING);
        manager.addTransaction(t);

        manager.editInfo(11, "30.5", 2);
        assertEquals(30.5, t.getAmount());
    }

    @Test
    public void testEditAmountNegative() {
        Transaction t = new Transaction(12, "Invalid", 10.0, Currency.SGD, Category.OTHER,
                LocalDate.now(), Status.PENDING);
        manager.addTransaction(t);

        assertThrows(InvalidCommand.class, () -> {
            manager.editInfo(12, "-100", 2);
        });
    }

    @Test
    public void testEditCurrency() throws Exception {
        Transaction t = new Transaction(13, "Book", 25.0, Currency.SGD, Category.OTHER,
                LocalDate.now(), Status.PENDING);
        manager.addTransaction(t);

        manager.editInfo(13, "usd", 3);
        assertEquals(Currency.USD, t.getCurrency());
    }
}

