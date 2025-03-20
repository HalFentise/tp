package seedu.duke;
import ui.Ui;
import parser.Parser;

public class Duke {
    private TransactionManager transactions;
    private Ui ui;

    public Duke() {
        transactions = new TransactionManager();
        ui = new Ui();
    }

    public void run() {
        ui.printWelcomeMessage();
        while (true) {
            String command = ui.readCommand();
            Parser.parser(command,ui,transactions);
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}
