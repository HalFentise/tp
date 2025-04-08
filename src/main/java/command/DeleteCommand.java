package command;

import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import ui.Ui;
import exceptions.InvalidCommand;

public class DeleteCommand extends Command {

    private final int transactionId;

    public DeleteCommand(int transactionId, TransactionManager transactions) throws InvalidCommand {
        Transaction t = transactions.searchTransaction(transactionId);
        if (t == null) {
            throw new InvalidCommand("Transaction not found! Please type in a valid id");
        }

        this.transactionId = transactionId;
    }

    @Override
    public void execute(TransactionManager transactions, Ui ui) {
        Transaction t = transactions.searchTransaction(transactionId);
        if (t == null) {
            ui.showError("Transaction already deleted.");
            return;
        }

        transactions.deleteExpense(transactionId);
        ui.printDeleteTask(t, transactions.getSize());
    }
}
