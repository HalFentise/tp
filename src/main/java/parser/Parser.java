package parser;

import command.DeleteCommand;
import command.NotifyCommand;
import command.SetBudgetCommand;
import exceptions.NullException;
import exceptions.InvalidCommand;
import seedu.duke.FinancialGoal;
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
    public static void parser(String userInput, Ui ui, TransactionManager transactions, FinancialGoal goal) {
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
                /*details = parts[1].split(IDENTIFIER_DESCRIPTION, 2);
                String description = details[1].trim();

                String[] amountParts = parts[2].split(IDENTIFIER_AMOUNT, 2);
                int amountValue = Integer.parseInt(amountParts[1].trim());  // Convert AMOUNT to integer

                // Extract CATEGORY (the part after 'c/')
                String[] categoryParts = parts[3].split(IDENTIFIER_CATEGORY, 2);
                String category = categoryParts[1].trim();  // CATEGORY after 'c/'

                // Extract DATE (the part after 't/')
                String[] dateParts = parts[4].split(IDENTIFIER_DATE, 2);
                String date = dateParts[1].trim(); // DATE after 'd/'*/
                /*String regex = "d/(.*)\\s+a/(\\d+)\\s+c/(.*)\\s+t/(.*)";

                // Compile the pattern
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(parts[1]);

                // Check if the input matches the pattern
                if (matcher.matches()) {
                    // Extract the values from the matched groups
                    String description = matcher.group(0).trim(); // Group 1: DESCRIPTION
                    int amountValue = Integer.parseInt(matcher.group(1).trim()); // Group 2: AMOUNT (converted to int)
                    String categoryString = matcher.group(2).trim(); // Group 3: CATEGORY
                    String date = matcher.group(3).trim(); // Group 4: DATE
*/
                String[] detail = {"description", "amount", "category", "date"};
                String[] patterns1 = {
                        "d/(.*?)(?:\\s+[ac]/|$)", // d/
                        "a/(.*?)(?:\\s+[dc]/|$)", // a/
                        "c/(.*?)(?:\\s+[da]/|$)",  // c/
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
                System.out.println("Goodbye! Hope to see you again!");
                System.exit(0);
                break;
            case COMMAND_SAVE:
                try {
                    int change = Integer.parseInt(parts[1].substring(1).trim());
                    if (parts[1].startsWith("+")) {
                        goal.addToSavings(change);
                    } else if (parts[1].startsWith("-")) {
                        goal.subFromSavings(change);
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    throw new InvalidCommand("Format invalid, try again! (save [+/-][amount])");
                }
                break;
            case COMMAND_GOAL:
                try {
                    parseGoalCommands(parts[1], goal);
                } catch (Exception e) {
                    throw new InvalidCommand("Format invalid, try again!");
                }
                break;
            default:
                throw new InvalidCommand(INVALID_INPUT);
            }
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
    }

    public static void parseGoalCommands(String command, FinancialGoal goal) throws Exception {
        String[] parts = command.toLowerCase().split(" ", 3);
        switch (parts[1]) {
        case GOAL_TARGET:
            goal.setTargetAmount(Integer.parseInt(parts[2]));
            break;
        case GOAL_DESC:
            goal.setDescription(parts[2]);
            break;
        case GOAL_TITLE:
            goal.setGoal(parts[2]);
            break;
        case GOAL_STATUS:
            if (goal.isBlank()) {
                throw new InvalidCommand("Empty goal.");
            } else {
                goal.checkGoalStatus();
            }
            break;
        case GOAL_NEW:
            goal.createNewGoal();
            break;
        default:
            if (goal.isBlank()) {
                goal.createNewGoal();
            } else {
                printGoal(goal);
            }
            // Print goal info / create new goal if empty
        }
    }
}
