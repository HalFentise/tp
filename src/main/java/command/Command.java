package command;

import exceptions.NullException;
import ui.Ui;
import seedu.duke.TransactionManager;

public abstract class Command {
    /**
     * Executes the command by performing operations on the given task list, UI, and storage.
     *
     * @param transactions The task list to operate on.
     * @param ui       The user interface for displaying messages.
     //* @param storage  The storage for saving the updated task list.
     * @throws NullException If an error occurs during execution.
     */
    public abstract void execute(TransactionManager transactions, Ui ui) throws NullException;

    /**
     * Determines if this command exits the Luke application.
     *
     * @return true if the command exits the program, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
