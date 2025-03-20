package parser;

import command.NotifyCommand;
import command.SetBudgetCommand;
import exceptions.NullException;
import seedu.duke.TransactionManager;
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
    public static void parser(String userInput, Ui ui, TransactionManager transaction) {
        String[] parts = userInput.split(" ");
        String commandType = parts[0];
        String[] details;

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
