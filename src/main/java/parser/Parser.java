package parser;

import java.util.regex.*;
import command.NotifyCommand;
import command.SetBudgetCommand;
import exceptions.NullException;
import exceptions.InvalidCommand;
import seedu.duke.TransactionManager;
import enumStructure.Category;
import ui.Ui;

import static constant.Constant.*;

public class Parser {
    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param userInput The raw user input string.
     * @return A {@code Command} object corresponding to the input.
     * @throws NullException If the input is invalid or missing required details.
     */
    public static void parser(String userInput, Ui ui, TransactionManager transactions) {
        String[] parts = userInput.toLowerCase().split(" ",2);
        String commandType = parts[0];
        String[] details;

        try {
            switch (commandType) {
            default:
                throw new InvalidCommand("This is a invalid command");
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
                int amount = Integer.parseInt(results[1]);
                Category category = Category.valueOf(results[2].toUpperCase());
                ui.add();
                transactions.addTransaction(transactions.getNum() + 1,results[0],amount,category);
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
            case COMMAND_SEARCH:
                String keyWord = parts[1];
                ui.search();
                ui.printTransactions(transactions.searchTransaction(keyWord));
                break;
            }
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }

        if (commandType.equals(COMMAND_SET_BUDGET)) {
            details = parts[1].split(IDENTIFIER_AMOUNT, 2);
            int amount = Integer.parseInt(details[1]);
            return new SetBudgetCommand(amount);

        } else if (commandType.equals(COMMAND_NOTIFY)) {
            details = parts[1].split(IDENTIFIER_DESCRIPTION, 2);
            String description = details[1].trim();

            String[] amountParts = parts[2].split(IDENTIFIER_AMOUNT, 2);
            int amount = Integer.parseInt(amountParts[1].trim());  // Convert AMOUNT to integer

            // Extract CATEGORY (the part after 'c/')
            String[] categoryParts = parts[3].split(IDENTIFIER_CATEGORY, 2);
            String category = categoryParts[1].trim();  // CATEGORY after 'c/'

            // Extract DATE (the part after 't/')
            String[] dateParts = parts[4].split(IDENTIFIER_DATE, 2);
            String date = dateParts[1].trim(); // DATE after 'd/'

            return new NotifyCommand(description, amount, category, date);
        } else {
            throw new NullException(INVALID_INPUT);
        }
    }
}
