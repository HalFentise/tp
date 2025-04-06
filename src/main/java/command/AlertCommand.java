package command;

import exceptions.NullException;
import seedu.duke.TransactionManager;
import ui.Ui;
import static ui.ConsoleFormatter.*;

public class AlertCommand extends Command{
    /**
     * @throws NullException If the date format is invalid.
     */
    public AlertCommand(TransactionManager transactions, Ui ui) throws NullException {

        try {
            ui.PrintBudgetLimit(transactions);
            ui.listNotifications(transactions.getTransactions());
            printLine();
            ui.listPriorities(transactions.getTransactions());
            Ui.printRecurringTransactions(transactions.getTransactions());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Executes the command by searching for tasks scheduled on the specified date
     * and displaying the results to the user.
     *
     * @param transactions The task list containing all tasks.
     * @param ui           The user interface for displaying results.
     *                     //* @param storage  The storage system (not used in this command).
     * @throws NullException If an error occurs while processing the command.
     */
    @Override
    public void execute(TransactionManager transactions, Ui ui) throws NullException {
    }
}
