package command;

import enumStructure.Priority;
import exceptions.NullException;
import seedu.duke.TransactionManager;
import ui.Ui;

public class SetPriorityCommand extends Command {

    /**
     * @param index       The index for the corresponding transaction.
     * @param priorityStr The string representing the priority level want to set.
     * @throws NullException If the date format is invalid.
     */
    public SetPriorityCommand(int index, String priorityStr, TransactionManager transcations, Ui ui) throws NullException {
        Priority priority = Priority.valueOf(priorityStr.toUpperCase());

        try {
            transcations.getTransactions().get(index).setPriority(priority);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        ui.PrintPriority(transcations.getTransactions(), index);
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
