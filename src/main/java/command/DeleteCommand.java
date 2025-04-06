package command;

import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import ui.Ui;
import exceptions.InvalidCommand;

public class DeleteCommand extends Command {

    /**
     * Constructs a DeleteCommand using the transaction ID.
     *
     * @param transactionId The ID of the transaction to delete (NOT list index).
     */
    public DeleteCommand(int transactionId, TransactionManager transactions) throws InvalidCommand{

        Transaction t = transactions.searchTransaction(transactionId);
        if (t == null) {
            throw new InvalidCommand("Transaction not found! Please type in a valid id");
        }

        transactions.deleteExpense(transactionId);  // Call delete by ID
        Ui.printDeleteTask(t, transactions.getSize()); // Print after delete
    }

    @Override
    public void execute(TransactionManager transactions, Ui ui) {
    }
}
