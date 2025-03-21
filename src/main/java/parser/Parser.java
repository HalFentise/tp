package parser;

import command.DeleteCommand;
import command.NotifyCommand;
import command.SetBudgetCommand;
import exceptions.NullException;
import exceptions.InvalidCommand;
import seedu.duke.Transaction;
import seedu.duke.TransactionManager;
import enumStructure.Category;
import ui.Ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constant.Constant.*;

public class Parser {
    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param userInput The raw user input string.
     * @throws NullException If the input is invalid or missing required details.
     */
    public static void parser(String userInput, Ui ui, TransactionManager transactions) {
        String[] parts = userInput.toLowerCase().split(" ", 2);
        String commandType = parts[0];
        String[] details;
        int amount;

        try {
            switch (commandType) {
            case COMMAND_ADD:
                String[] fields = {"description", "amount", "category"};
                String[] patterns = {
                        "d/(.*?)(?:\\s+[ac]/|$)", // d/
                        "a/(.*?)(?:\\s+[dc]/|$)", // a/
                        "c/(.*?)(?:\\s+[da]/|$)"  // c/
                };

                String[] results = new String[fields.length];
                //match pattern
                for (int i = 0; i < fields.length; i++) {
                    Pattern pattern = Pattern.compile(patterns[i]);
                    Matcher matcher = pattern.matcher(parts[1]);

                    if (matcher.find()) {
                        results[i] = matcher.group(1).trim();
                    } else {
                        throw new InvalidCommand("No " + fields[i] + " found");
                    }
                }
                amount = Integer.parseInt(results[1]);
                Category category = Category.valueOf(results[2].toUpperCase());
                transactions.addTransaction(transactions.getNum() + 1, results[0], amount, category);
                ui.add(transactions.searchTransaction(transactions.getNum()));
                break;
            case COMMAND_LIST:
                if (parts.length > 1) {
                    throw new InvalidCommand("Invalid command");
                }
                ui.printTransactions(transactions.getTransactions());
                break;
            case COMMAND_TICK:
                int id = Integer.parseInt(parts[1]);
                transactions.tickTransaction(id);
                ui.tickTransaction(transactions.searchTransaction(id));
                break;
            case COMMAND_UNTICK:
                id = Integer.parseInt(parts[1]);
                transactions.unTickTransaction(id);
                ui.unTickTransaction(transactions.searchTransaction(id));
                break;
            case COMMAND_SEARCH:
                boolean isIndex = parts[1].startsWith("id-");
                String keyWord = isIndex ? parts[1].substring(3) : parts[1];
                ui.search(isIndex);
                ui.printTransactions(transactions.searchTransactionList(isIndex, keyWord, ui));
                break;
            case COMMAND_DELETE:
                int index = Integer.parseInt(parts[1]);
                new DeleteCommand(index - 1, transactions, ui);
                break;
            case COMMAND_SET_BUDGET:
                details = parts[1].split(IDENTIFIER_AMOUNT, 2);
                amount = Integer.parseInt(details[1]);
                new SetBudgetCommand(amount, transactions, ui);
                break;
            case COMMAND_NOTIFY:
                String[] detail = {"description", "amount", "category", "date"};
                String[] patterns1 = {
                        "d/(.*?)(?:\\s+[ac]/|$)", // d/
                        "a/(.*?)(?:\\s+[dc]/|$)", // a/
                        "c/(.*?)(?:\\s+[at]/|$)",  // c/
                        "t/(.*?)(?:\\s+[da]/|$)"  // t/
                };

                String[] result = new String[detail.length];
                //match pattern
                for (int i = 0; i < detail.length; i++) {
                    Pattern pattern = Pattern.compile(patterns1[i]);
                    Matcher matcher = pattern.matcher(parts[1]);

                    if (matcher.find()) {
                        result[i] = matcher.group(1).trim();
                    } else {
                        throw new InvalidCommand("No " + detail[i] + " found");
                    }
                }

                amount = Integer.parseInt(result[1]);
                String categoryString = result[2].toUpperCase();
                String date = result[3];

                new NotifyCommand(result[0], amount, categoryString, date, transactions, ui);
                break;
            case COMMAND_RECUR:
                int slashIndex = parts[1].indexOf("/");
                try {
                    int transactionId = Integer.parseInt(parts[1].substring(0, slashIndex).trim());
                    int recurringPeriod = Integer.parseInt(parts[1].substring(slashIndex + 1).trim());
                    transactions.setRecur(transactionId, recurringPeriod);
                    ui.setPeriod(transactions.searchTransaction(transactionId), recurringPeriod);
                } catch (Exception e) {
                    throw new InvalidCommand("Format invalid, try again! (recur [id]/[period])");
                }
                break;
            case COMMAND_EXIT:
                ui.printExit();
                System.exit(0);
                break;
            default:
                throw new InvalidCommand(INVALID_INPUT);
            }
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
    }
}
