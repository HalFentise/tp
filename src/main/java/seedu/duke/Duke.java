package seedu.duke;

import seedu.duke.budget.BudgetList;
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
        storage = new Storage();
        transactions = new TransactionManager();

        // ✅ 加载 Transaction 数据
        ArrayList<Transaction> savedTransactions = storage.loadTransactions();
        for (Transaction t : savedTransactions) {
            transactions.addTransaction(t);
        }

        // ✅ 加载 Budget 数据
        BudgetList loadedBudgets = storage.loadBudgets();
        transactions.setBudgetList(loadedBudgets);

        // ✅ 加载储蓄目标
        goal = storage.loadGoal();

        // 安全性断言
        assert ui != null : "UI should be initialized";
        assert goal != null : "FinancialGoal should be initialized";
        assert storage != null : "Storage should be initialized";
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
