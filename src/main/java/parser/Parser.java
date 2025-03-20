package parser;

import command.DeleteCommand;
import command.NotifyCommand;
import command.SetBudgetCommand;
import exceptions.NullException;
import seedu.duke.TransactionManager;
import ui.Ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String[] parts = userInput.split(" ", 2);
        String commandType = parts[0];
        String[] details;

        switch (commandType) {
        case COMMAND_DELETE:
            int index = Integer.parseInt(parts[1]);
            new DeleteCommand(index);

        case COMMAND_SET_BUDGET:
            details = parts[1].split(IDENTIFIER_AMOUNT, 2);
            int amount = Integer.parseInt(details[1]);
            new SetBudgetCommand(amount);
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
            String regex = "d/(.*)\\s+a/(\\d+)\\s+c/(.*)\\s+t/(.*)";

            // Compile the pattern
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(parts[1]);

            // Check if the input matches the pattern
            if (matcher.matches()) {
                // Extract the values from the matched groups
                String description = matcher.group(0).trim(); // Group 1: DESCRIPTION
                int amountValue = Integer.parseInt(matcher.group(1).trim()); // Group 2: AMOUNT (converted to int)
                String category = matcher.group(2).trim(); // Group 3: CATEGORY
                String date = matcher.group(3).trim(); // Group 4: DATE

                new NotifyCommand(description, amountValue, category, date);
            }

        default:
            throw new NullException(INVALID_INPUT);
        }
    }
}
