package seedu.duke;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import enumStructure.Category;
import enumStructure.Currency;
import enumStructure.Status;

class StorageTest {

    private static final String TEST_FILE_PATH = "data/test_transactions.csv";

    @Test
    void testSaveAndLoadTransactions() {
        Storage storage = new Storage();
        ArrayList<Transaction> transactions = new ArrayList<>();

        Transaction t1 = new Transaction(1, "Dinner", 100,
                Currency.USD, Category.FOOD, LocalDate.of(2025, 4, 1), Status.PENDING);
        Transaction t2 = new Transaction(2, "Taxi", 50, Currency.USD,
                Category.TRANSPORTATION, LocalDate.of(2025, 4, 2), Status.COMPLETED);

        transactions.add(t1);
        transactions.add(t2);

        // 1. 测试保存
        storage.saveTransactions(transactions);

        // 2. 读取回数据
        ArrayList<Transaction> loadedTransactions = storage.loadTransactions();

        // 3. 验证数据是否一致
        assertEquals(2, loadedTransactions.size());
        assertEquals("Dinner", loadedTransactions.get(0).getDescription());
        assertEquals(100, loadedTransactions.get(0).getAmount());
        assertEquals("Taxi", loadedTransactions.get(1).getDescription());

        // 4. 清理测试文件
        new File(TEST_FILE_PATH).delete();
    }
}

