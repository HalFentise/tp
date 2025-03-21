package seedu.duke;
import ui.Ui;
import parser.Parser;

public class Duke {
    private TransactionManager transactions;
    private Ui ui;
    private FinancialGoal goal;

    public Duke() {
        transactions = new TransactionManager();
        ui = new Ui();
        goal = new FinancialGoal();
    }

    public void run() {
        ui.printWelcomeMessage();
        while (true) {
            String command = ui.readCommand();
            Parser.parser(command, ui, transactions, goal);
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}
