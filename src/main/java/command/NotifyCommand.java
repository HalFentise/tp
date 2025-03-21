package command;

import exceptions.NullException;
import seedu.duke.TransactionManager;
import ui.Ui;

public class NotifyCommand extends Command {

    public NotifyCommand(String description, int amount, String categoryString, String date, TransactionManager transactions, Ui ui) {
        try {
            transactions.notify(description, amount, categoryString, date);
        } catch (NullException e) {
            ui.showError(e.getMessage());
            // Display the all notifications
        }
        ui.listNotification(transactions.getTransactions(), description);
    }


    @Override
    public void execute(TransactionManager transactions, Ui ui) throws NullException {
        // Search for tasks matching the specified date
       /* try {
            transactions.notify(description, amount, category, date);
        } catch (NullException e) {
            ui.showError(e.getMessage());
            // Display the all notifications
        }
        ui.listNotification(transactions.getTransactions(), description);*/
    }
}
