package command;

import exceptions.NullException;
import seedu.duke.Storage;
import ui.Ui;
import seedu.duke.TransactionManager;

//@@author Lukapeng77
public abstract class Command {
    /**
     * Executes the command by performing operations on the given task list, UI, and storage.
     *
     * @param transactions The transaction manager to operate on.
     * @param ui       The user interface for displaying messages.
     * @throws NullException If an error occurs during execution.
     */
    public abstract void execute(TransactionManager transactions, Ui ui) throws NullException;

    //@@author gu0y1
    /**
     *  Overloaded execute with Storage, default throws UnsupportedOperationException.
     */
    public void execute(TransactionManager transactions, Ui ui, Storage storage) throws NullException {
        throw new UnsupportedOperationException("This command does not support storage-based execute().");
    }

    /**
     * Determines if this command exits the application.
     *
     * @return true if the command exits the program, false otherwise.
     */
    public boolean isExit() {
        return false;
    }
}
