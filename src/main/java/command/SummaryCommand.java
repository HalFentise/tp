package command;

import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import ui.Ui;

import java.time.LocalDate;
import java.util.List;

public class SummaryCommand extends Command{
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final TransactionManager transactions;

    public SummaryCommand(LocalDate start, LocalDate end, TransactionManager transactions, Ui ui) {
        this.startDate = start;
        this.endDate = end;
        this.transactions = transactions;
        List<Transaction> filteredTransactions = transactions.getTransactionsBetween(start, end);
        double total = filteredTransactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
        ui.printSummary(filteredTransactions, total, start, end);

    }

    @Override
    public void execute(TransactionManager transactions, Ui ui) {
        /*List<Transaction> filteredTransactions = transactions.getTransactionsBetween(startDate, endDate);
        double total = filteredTransactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
        ui.printSummary(filteredTransactions, total, startDate, endDate);*/
    }
}
