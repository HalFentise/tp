package seedu.duke;

import ui.Ui;
import parser.Parser;
import java.util.ArrayList;

public class Duke {
    private TransactionManager transactions;
    private Ui ui;
    private FinancialGoal goal;
    private Storage storage;

    public Duke() {
        ui = new Ui();
        goal = new FinancialGoal();
        storage = new Storage();
        // load data
        transactions = new TransactionManager();
    }

    public void run() {
        ui.printWelcomeMessage();
        ArrayList<Transaction> savedTransactions = storage.loadTransactions();
        for (Transaction t : savedTransactions) {
            transactions.addTransaction(t);
        }
        transactions.remindRecurringTransactions();
        while (true) {
            String command = ui.readCommand();
            Parser.parser(command, ui, transactions, goal, storage);
        }
    }

    public static void main(String[] args) {
        new Duke().run();
        //assert false : "dummy assertion set to fail";
    }
}

