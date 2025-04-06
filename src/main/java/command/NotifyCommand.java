package command;

import exceptions.NullException;
import seedu.duke.TransactionManager;
import ui.Ui;

import java.time.LocalDate;

public class NotifyCommand extends Command {
    public NotifyCommand(String description, String categoryString,
                         LocalDate date, TransactionManager transactions, Ui ui) {
        try {
            transactions.notify(description, categoryString, date);
        } catch (NullException e) {
            ui.showError(e.getMessage());
            // Display the all notifications
        }
        ui.listNotification(transactions.getTransactions(), description);
    }

    @Override
    public void execute(TransactionManager transactions, Ui ui) throws NullException {
    }
}
