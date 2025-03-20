package command;

import exceptions.NullException;
import seedu.duke.TransactionManager;
import ui.Ui;

public class NotifyCommand extends Command {
    protected String description;
    protected int amount;
    protected String category;
    protected String date;

    public NotifyCommand(String description, int amount, String categoryString, String date) {
        this.date = date;
        this.category = categoryString;
        this.description = description;
        this.amount = amount;
    }


    @Override
    public void execute(TransactionManager transactions, Ui ui) throws NullException {
        // Search for tasks matching the specified date
        try {
            transactions.notify(description, amount, category, date);
        } catch (NullException e) {
            ui.showError(e.getMessage());
            // Display the all notifications
        }
        ui.listNotification(transactions.getTransactions(), description);
    }
}
