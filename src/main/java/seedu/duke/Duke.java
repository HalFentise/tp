package seedu.duke;

import ui.Ui;
import parser.Parser;

public class Duke {
    private TransactionManager transactions;
    private final Ui ui;
    private FinancialGoal goal;
    private final Storage storage;

    public Duke() {
        ui = new Ui();
        storage = new Storage();
        transactions = new TransactionManager();
        transactions.setStorage(storage);

        storage.load(transactions);
        transactions.loadBudgetFromStorage();
        goal = storage.loadGoal();

        assert goal != null : "FinancialGoal should be initialized";
        assert transactions != null : "TransactionManager should be initialized";
    }

    public void run() {
        ui.printWelcomeMessage();
        ui.printSavingOverview(goal);
        transactions.remindRecurringTransactions();

        while (true) {
            String command = ui.readCommand();
            if (command == null || command.trim().isEmpty()) {
                continue;
            }
            Parser.parser(command, ui, transactions, goal, storage);
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}
