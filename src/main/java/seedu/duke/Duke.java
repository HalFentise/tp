package seedu.duke;

import ui.Ui;
import parser.Parser;

/**
 * The {@code Duke} class serves as the entry point of the Budget flow application.
 * It initializes all core components including UI, storage, transaction manager,
 * and financial goal. It also handles the main interaction loop for reading and
 * executing user commands.
 */
public class Duke {
    /** Manages all transactions and budget logic. */
    private final TransactionManager transactions;

    /** Handles user input and output. */
    private final Ui ui;

    /** Represents the user's financial goal. */
    private final FinancialGoal goal;

    /** Handles loading and saving data from and to the disk. */
    private final Storage storage;

    /**
     * Constructs a new {@code Duke} instance.
     * Initializes UI, storage, and transaction manager.
     * Loads transactions, budget, and financial goal from storage.
     * Includes assertions to ensure key components are initialized.
     */
    public Duke() {
        ui = new Ui();
        storage = new Storage();
        transactions = new TransactionManager();
        transactions.setStorage(storage);

        storage.load(transactions);
        transactions.loadBudgetFromStorage();
        goal = storage.loadGoal();

        assert goal != null : "FinancialGoal should be initialized";
    }

    /**
     * Starts the application loop.
     * Greets the user, prints saving overview, reminds about recurring transactions,
     * and continuously reads and processes user commands.
     */
    public void run() {
        ui.printWelcomeMessage();
        ui.printSavingOverview(goal);
        ui.printStatisticsOverview(transactions);
        transactions.remindRecurringTransactions();

        //@@author
        while (true) {
            String command = ui.readCommand();
            if (command == null || command.trim().isEmpty()) {
                continue;
            }
            Parser.parser(command, ui, transactions, goal, storage);
        }
    }

    /**
     * The main method. Entry point of the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        new Duke().run();
    }
}
