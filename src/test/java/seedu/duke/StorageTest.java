package seedu.duke;

import org.junit.jupiter.api.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Status;
import enumStructure.Priority;
import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {
    private Storage storage;
    private File testFolder;
    private File testFile;

    @BeforeEach
    public void setUp() throws IOException {
        storage = new Storage();
        testFolder = new File("data");
        testFile = new File("data/transactions.csv");
        if (!testFolder.exists()) {
            testFolder.mkdirs();
        }
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @AfterEach
    public void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testSaveAndLoadTransactions_success() {
        ArrayList<Transaction> originalList = new ArrayList<>();
        Transaction t1 = new Transaction(1, "Coffee", 5.0, Currency.SGD, Category.FOOD, LocalDate.now(), Status.PENDING);
        t1.setRecurringPeriod(7);
        t1.setPriority(Priority.HIGH);
        t1.complete();
        originalList.add(t1);

        Transaction t2 = new Transaction(2, "Movie", 12.5, Currency.USD, Category.ENTERTAINMENT, LocalDate.now(), Status.COMPLETED);
        t2.setRecurringPeriod(0);
        t2.setPriority(Priority.LOW);
        t2.delete();
        originalList.add(t2);

        storage.saveTransactions(originalList);

        ArrayList<Transaction> loadedList = storage.loadTransactions();

        assertEquals(originalList.size(), loadedList.size());
        Transaction loaded1 = loadedList.get(0);
        assertEquals("Coffee", loaded1.getDescription());
        assertEquals(5.0, loaded1.getAmount());
        assertEquals(Currency.SGD, loaded1.getCurrency());
        assertEquals(Category.FOOD, loaded1.getCategory());
        assertEquals(Priority.HIGH, loaded1.getPriority());
        assertTrue(loaded1.isCompleted());
        assertFalse(loaded1.isDeleted());

        Transaction loaded2 = loadedList.get(1);
        assertEquals("Movie", loaded2.getDescription());
        assertEquals(Currency.USD, loaded2.getCurrency());
        assertEquals(Category.ENTERTAINMENT, loaded2.getCategory());
        assertEquals(Priority.LOW, loaded2.getPriority());
        assertFalse(loaded2.isCompleted());
        assertTrue(loaded2.isDeleted());
    }

    @Test
    public void testLoadTransactions_fileNotExist_returnsEmptyList() {
        if (testFile.exists()) {
            testFile.delete();
        }
        ArrayList<Transaction> transactions = storage.loadTransactions();
        assertNotNull(transactions);
        assertEquals(0, transactions.size());
    }

    @Test
    public void testSaveTransactions_createsFile() {
        ArrayList<Transaction> list = new ArrayList<>();
        list.add(new Transaction(1, "Test", 10.0, Currency.SGD, Category.OTHER, LocalDate.now(), Status.PENDING));
        storage.saveTransactions(list);
        assertTrue(testFile.exists());
    }

    @Test
    public void testLoadTransactions_invalidLine_skippedGracefully() throws IOException {
        if (!testFolder.exists()) {
            testFolder.mkdirs();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
        writer.write("1,Test,notANumber,SGD,FOOD,2024-01-01,PENDING,0,false,false,LOW");
        writer.newLine();
        writer.close();

        ArrayList<Transaction> loaded = storage.loadTransactions();
        assertTrue(loaded.isEmpty());
    }
}


