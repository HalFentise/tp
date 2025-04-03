package seedu.duke;

import enumStructure.Status;
import enumStructure.Category;
import enumStructure.Currency;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

class TransactionTest {

    @Test
    void testTransactionCreation() {
        Transaction t = new Transaction(1, "Taxi", 100, Currency.USD, Category.FOOD,
                LocalDate.parse("2025-04-01"), Status.PENDING);
        assert t.getId() == 1 : "ID should be 1";
        assert t.getDescription().equals("Taxi") : "Description should be 'Taxi'";
        assert t.getAmount() == 100 : "Amount should be 100";
    }
}
