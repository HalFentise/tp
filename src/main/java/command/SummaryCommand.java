package command;

import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import ui.Ui;

import java.time.LocalDate;
import java.util.List;

public class SummaryCommand extends Command{

    public SummaryCommand(LocalDate start, LocalDate end, TransactionManager transactions, Ui ui) {
        List<Transaction> filteredTransactions = transactions.getTransactionsBetween(start, end);
        double total = filteredTransactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
        ui.printSummary(filteredTransactions, total, start, end);

    }

    @Override
    public void execute(TransactionManager transactions, Ui ui) {
    }
}
