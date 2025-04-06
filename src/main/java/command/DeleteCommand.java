package command;

import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import ui.Ui;

public class DeleteCommand extends Command {
    private final int transactionId;

    /**
     * Constructs a DeleteCommand using the transaction ID.
     *
     * @param transactionId The ID of the transaction to delete (NOT list index).
     */
    public DeleteCommand(int transactionId, TransactionManager transactions, Ui ui) {
        this.transactionId = transactionId;

        Transaction t = transactions.searchTransaction(transactionId);
        if (t == null) {
            ui.showError("No transaction found with ID: " + transactionId);
            return;
        }

        transactions.deleteExpense(transactionId);  // Call delete by ID
        Ui.printDeleteTask(t, transactions.getSize()); // Print after delete
    }

    @Override
    public void execute(TransactionManager transactions, Ui ui) {
        // Already executed in constructor; you can leave this empty or move logic here if needed.
    }
}
