package parser;

import command.*;

import enums.Currency;
import exceptions.NullException;
import exceptions.InvalidCommand;
import seedu.duke.*;
import seedu.duke.budget.BudgetMode;
import enums.Category;
import ui.ConsoleFormatter;
import ui.Ui;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static constant.Constant.*;

public class Parser {
    /**
     * Parses the user input and returns the corresponding command.
     *
     * @param userInput The raw user input string.
     */
    public static void parser(String userInput, Ui ui, TransactionManager transactions,
                              FinancialGoal goal, Storage storage) {
        String[] parts = userInput.toLowerCase().split(" ", 2);
        String commandType = parts[0];
        String[] details;
        double amount;
        int index;
        int id;
        LocalDate date;
        String[] fields;

        //@@author HalFentise
        try {
            switch (commandType) {
                case COMMAND_HELP:
                    ui.help();
                    break;
                case COMMAND_ADD:
                    if (parts.length == 1 || parts[1].isBlank()) {

                        Command addWizard = new AddWizardCommand();
                        addWizard.execute(transactions, ui, storage);
                        break;
                    }

                    fields = new String[]{"description", "amount", "category", "date"};
                    String[] patterns = {
                            "d/(.*?)(?:\\s+[act]/|$)", // description
                            "a/(.*?)(?:\\s+[dct]/|$)", // amount
                            "c/(.*?)(?:\\s+[dat]/|$)", // category
                            "t/(.*?)(?:\\s+[dac]/|$)", // date (optional)
                    };

                    String[] results = new String[fields.length];

                    for (int i = 0; i < fields.length; i++) {
                        Pattern pattern = Pattern.compile(patterns[i]);
                        Matcher matcher = pattern.matcher(parts[1]);

                        if (matcher.find()) {
                            results[i] = matcher.group(1).trim();
                        } else if (!fields[i].equals("date")) {
                            throw new InvalidCommand("No " + fields[i] + " found");
                        } else {
                            results[i] = null; // date is optional
                        }
                    }

                    amount = Double.parseDouble(results[1]);
                    Category category = parseCategory(results[2], ui);
                    date = parseToLocalDate(results[3]);

                    boolean success = transactions.addTransaction(results[0], amount, category, date);

                    if (success) {
                        ui.add(transactions.searchTransaction(transactions.getNum()));
                        storage.saveTransactions(transactions.getTransactions());
                    } else {
                        throw new InvalidCommand("Cannot add new transaction! Budget limit exceeded!");
                    }
                    break;

                case COMMAND_LIST:
                    if (parts.length > 1) {
                        throw new InvalidCommand("Invalid command");
                    }
                    ui.printTransactions(transactions.getTransactions());
                    break;
                case COMMAND_TICK:
                    id = Integer.parseInt(parts[1]);
                    transactions.tickTransaction(id);
                    ui.tickTransaction(transactions.searchTransaction(id));
                    storage.saveTransactions(transactions.getTransactions());
                    break;
                case COMMAND_UNTICK:
                    id = Integer.parseInt(parts[1]);
                    transactions.unTickTransaction(id);
                    ui.unTickTransaction(transactions.searchTransaction(id));
                    storage.saveTransactions(transactions.getTransactions());
                    break;
                    //@@ author
                case COMMAND_STATUS:
                    try {
                        if (parts.length == 1 || parts[1].isBlank()) {
                            Command statusWizard = new StatusWizardCommand();
                            statusWizard.execute(transactions, ui, storage);
                        } else {
                            id = Integer.parseInt(parts[1]);
                            Transaction t = transactions.searchTransaction(id);
                            if (t == null) {
                                throw new InvalidCommand("Transaction not found.");
                            }

                            if (t.isCompleted()) {
                                t.notComplete();
                                ui.unTickTransaction(t);
                            } else {
                                t.complete();
                                ui.tickTransaction(t);
                            }

                            storage.saveTransactions(transactions.getTransactions());
                        }
                    } catch (NumberFormatException e) {
                        throw new InvalidCommand("Invalid transaction ID format.");
                    } catch (Exception e) {
                        throw new InvalidCommand("Failed to update status: " + e.getMessage());
                    }
                    break;

                //@@author yangyi-zhu
                case COMMAND_SEARCH:
                    boolean isIndex = parts[1].startsWith("id-");
                    String keyWord = isIndex ? parts[1].substring(3) : parts[1];
                    ui.search(isIndex);
                    ui.printTransactions(transactions.searchTransactionList(isIndex, keyWord));
                    break;
                case COMMAND_EDIT:
                    try {
                        if (parts.length == 1 || parts[1].isBlank()) {
                            Command editWizard = new EditWizardCommand();
                            editWizard.execute(transactions, ui, storage);
                        } else {
                            parseEditCommands(parts[1], ui, transactions);
                        }
                        storage.saveTransactions(transactions.getTransactions());
                    } catch (InvalidCommand ic) {
                        throw new InvalidCommand(ic.getMessage());
                    } catch (Exception e) {
                        throw new InvalidCommand("Format invalid, try again! (edit [attribute] [id] [value])");
                    }
                    break;

                //@@author

                //@@author Lukapeng77
                case COMMAND_DELETE:
                    id = Integer.parseInt(parts[1]);
                    new DeleteCommand(id, transactions);
                    storage.saveTransactions(transactions.getTransactions());
                    break;
                case COMMAND_CLEAR:
                    transactions.clear();
                    storage.saveTransactions(transactions.getTransactions());
                    ui.printClear();
                    break;
                case COMMAND_SET_BUDGET:
                    details = parts[1].split(IDENTIFIER_AMOUNT, 2);
                    amount = Double.parseDouble(details[1]);
                    //@@author

                    if (Double.isInfinite(amount) || Double.isNaN(amount)) {
                        System.out.println("Invalid input: amount is too large, too small, or not a number.");
                    }

                    new SetBudgetCommand(amount, transactions);
                    storage.saveTransactions(transactions.getTransactions());
                    break;
                case FIND_DATE:
                    String time = parts[1];
                    transactions.getUpcomingTransactions(time);
                    break;
                    //@@author HalFentise
                case COMMAND_CURRENCY:
                    Currency currency = parseCurrency(ui);
                    transactions.setDefaultCurrency(currency);
                    storage.saveDefaultCurrency(currency);
                    break;
                    //@@author
                //@@author Lukapeng77
                case COMMAND_NOTIFY:
                    String[] detail = {"description", "category", "date"};
                    String[] notifyPatterns = {
                            "d/(.*?)(?:\\s+[ac]/|$)", // d/
                            "c/(.*?)(?:\\s+[at]/|$)",  // c/
                            "t/(.*?)(?:\\s+[da]/|$)"  // t/
                    };

                    String[] result = new String[detail.length];
                    //match pattern
                    for (int i = 0; i < detail.length; i++) {
                        Pattern pattern = Pattern.compile(notifyPatterns[i]);
                        Matcher matcher = pattern.matcher(parts[1]);

                        if (matcher.find()) {
                            result[i] = matcher.group(1).trim();
                        } else {
                            throw new InvalidCommand("No " + detail[i] + " found");
                        }
                    }

                    String categoryString = result[1].toUpperCase();
                    date = parseToLocalDate(result[2]);

                    new NotifyCommand(result[0], categoryString, date, transactions, ui);
                    storage.saveTransactions(transactions.getTransactions());
                    break;
                case COMMAND_ALERT:
                    if (parts.length > 1 && !parts[1].trim().isEmpty()) {
                        throw new InvalidCommand("Invalid command");
                    }
                    new AlertCommand(transactions, ui);
                    storage.saveTransactions(transactions.getTransactions());
                    break;

                case COMMAND_SET_PRIORITY:
                    try {
                        details = parts[1].split(" ", 2);
                        index = Integer.parseInt(details[0]);

                        new SetPriorityCommand(index - 1, details[1], transactions, ui);
                    } catch (NullException e) {
                        throw new InvalidCommand("Invalid input format, should be (priority [id] [priority_level])");
                    }
                    storage.saveTransactions(transactions.getTransactions());
                    break;
                case COMMAND_SUMMARY:
                    try {
                        if (parts.length < 2) {
                            throw new InvalidCommand("Missing date range. Use: summary from/YYYY-MM-DD to/YYYY-MM-DD");
                        }

                        Pattern pattern = Pattern.compile("from/(\\d{4}-\\d{2}-\\d{2})\\s+to/(\\d{4}-\\d{2}-\\d{2})");
                        Matcher matcher = pattern.matcher(parts[1]);

                        if (matcher.matches()) {
                            LocalDate start = LocalDate.parse(matcher.group(1));
                            LocalDate end = LocalDate.parse(matcher.group(2));
                            new SummaryCommand(start, end, transactions, ui);
                        } else {
                            throw new InvalidCommand("Invalid summary command format. " +
                                    "Follow this input format: summary from/YYYY-MM-DD to/YYYY-MM-DD");
                        }
                    } catch (DateTimeParseException e) {
                        throw new InvalidCommand("Invalid date format. Follow this format: YYYY-MM-DD.");
                    }
                    break;
                //@@author Lukapeng77
                case COMMAND_CONVERT:
                    try {
                        Pattern pattern = Pattern.compile("id/(\\d+)\\s+to/(\\w+)", Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(parts[1]);

                        if (!matcher.find()) {
                            throw new InvalidCommand("Invalid convert format. Use: convert id/TRANSACTION_ID to/CURRENCY");
                        }

                        int transactionId = Integer.parseInt(matcher.group(1).trim());
                        Currency targetCurrency = Currency.valueOf(matcher.group(2).trim().toUpperCase());

                        new ConvertCommand(transactionId, targetCurrency, transactions, ui);
                        storage.saveTransactions(transactions.getTransactions());

                    } catch (IllegalArgumentException e) {
                        throw new InvalidCommand("Invalid currency code provided.");
                    } catch (Exception e) {
                        throw new InvalidCommand("Error processing convert command.");
                    }
                    break;

                //@@author yangyi-zhu
                case COMMAND_RECUR:
                    int slashIndex = parts[1].indexOf("/");
                    try {
                        int transactionId = Integer.parseInt(parts[1].substring(0, slashIndex).trim());
                        int recurringPeriod = Integer.parseInt(parts[1].substring(slashIndex + 1).trim());
                        transactions.setRecur(transactionId, recurringPeriod);
                        ui.setPeriod(transactions.searchTransaction(transactionId), recurringPeriod);
                    } catch (StringIndexOutOfBoundsException | NumberFormatException fe) {
                        throw new InvalidCommand("Format invalid, try again! (recur [id]/[period])");
                    } catch (Exception e) {
                        throw new InvalidCommand("Transaction not found!");
                    }
                    storage.saveTransactions(transactions.getTransactions());
                    break;
                //@@author
                case COMMAND_EXIT:
                    ui.printExit();
                    storage.saveTransactions(transactions.getTransactions());
                    System.exit(0);
                    break;
                case "saving":
                    SavingMode.enter(ui, goal, transactions, storage);
                    break;
                case "budget":
                    BudgetMode.enter(ui, transactions.getBudgetList(), transactions, storage);
                    break;
                case "balance":
                    double bal = transactions.getCurrentBalanceInSGD();
                    ui.printBalanceOverview(bal);
                    break;

                case "stats":
                    ui.printStatisticsOverview(transactions);
                    break;
                //@@author yangyi-zhu
                case COMMAND_GOAL:
                    goal.updateExpenses(transactions);
                    try {
                        String goalTag = parts.length < 2 ? "placeholder" : parts[1];
                        parseGoalCommands(goalTag, ui, goal);
                    } catch (Exception e) {
                        throw new InvalidCommand("Format invalid, try again!");
                    }
                    break;
                //@@author
                default:
                    throw new InvalidCommand(INVALID_INPUT);
            }
        } catch (Exception e) {
            ui.showError(e.getMessage());
        }
    }

    //@@author yangyi-zhu
    /**
     * Attempts to parse user commands pertaining to financial goal.
     * This method handles setting goal properties such as target amount,
     * description, title, and checking the status of the goal. It also allows
     * creating a new goal or simply printing the goal.
     *
     * @param command The user input command to be parsed.
     * @param ui The UI instance for interacting with the user.
     * @param goal The current financial goal to be modified or queried.
     * @throws Exception If the command is invalid or if the goal is empty when required.
     */
    public static void parseGoalCommands(String command, Ui ui, FinancialGoal goal) throws Exception {
        String[] parts = command.toLowerCase().split(" ", 2);
        assert goal != null : "Goal must not be null";

        switch (parts[0]) {
            case GOAL_TARGET:
                goal.setTargetAmount(Integer.parseInt(parts[1]));
                ui.setGoalTarget(goal);
                break;
            case GOAL_DESC:
                goal.setDescription(parts[1]);
                ui.setGoalDescription(goal);
                break;
            case GOAL_TITLE:
                goal.setGoal(parts[1]);
                ui.setGoalTitle(goal);
                break;
            case GOAL_STATUS:
                if (goal.isBlank()) {
                    throw new InvalidCommand("Empty goal.");
                } else {
                    goal.checkGoalStatus();
                }
                break;
            case GOAL_NEW:
                goal.createNewGoal(ui);
                break;
            default:
                if (goal.isBlank()) {
                    goal.createNewGoal(ui);
                } else {
                    ui.printGoal(goal);
                }
        }
    }

    /**
     * Attempts to parse user commands to edit transactions.
     * This method modifies transaction attributes such as description, category,
     * amount, and currency based on user input. It validates the transaction ID and
     * the attribute type, and handles incorrect inputs with custom exceptions.
     *
     * @param command The user input command to be parsed.
     * @param ui The UI instance for interacting with the user.
     * @param transactions The transaction manager handling all transaction data.
     * @throws Exception If the attribute is unknown, ID is out of range, or the new value is invalid.
     */
    public static void parseEditCommands(String command, Ui ui, TransactionManager transactions) throws Exception {
        String[] fields = command.toLowerCase().split(" ", 3);
        String attribute = fields[0];
        int id = Integer.parseInt(fields[1]);
        if (id < 0) {
            throw new InvalidCommand("ID is out of range!");
        }
        String value = fields[2];

        switch (attribute) {
            case EDIT_DESC:
                transactions.editInfo(id, value, 0);
                ui.printEdited(value, 0);
                break;
            case EDIT_CAT:
                value = value.toUpperCase();
                try {
                    transactions.editInfo(id, value, 1);
                } catch (Exception e) {
                    throw new InvalidCommand("Unknown category, try again!");
                }
                ui.printEdited(value, 1);
                break;
            case EDIT_AM:
                transactions.editInfo(id, value, 2);
                ui.printEdited(value, 2);
                break;
            case EDIT_CURR:
                try {
                    transactions.editInfo(id, value, 3);
                } catch (Exception e) {
                    throw new InvalidCommand("Unknown currency, try again!");
                }
                ui.printEdited(value.toUpperCase(), 3);
                break;
            default:
                throw new InvalidCommand("Unknown attribute!");
        }
    }

    //@@author
    /**
     * Tries to parse a string into a valid Category enum, case-insensitively.
     * If the input is invalid, shows a list of valid categories and asks user to choose one.
     */
    public static Category parseCategory(String userInput, Ui ui) {
        String trimmedInput = userInput.trim();
        for (Category category : Category.values()) {
            if (category.name().equalsIgnoreCase(trimmedInput)) {
                return category;
            }
        }

        ui.showError("Invalid category: \"" + userInput + "\"");
        ui.printCategoryChoice();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            ui.printCategoryHint();
            String choice = scanner.nextLine();
            if (choice.equals("exit")) {
                return Category.OTHER;
            }
            try {
                int selected = Integer.parseInt(choice);
                if (selected >= 1 && selected <= Category.values().length) {
                    ui.printCategoryChoose();
                    return Category.values()[selected - 1];
                }
            } catch (NumberFormatException ignored) {
                System.out.println("Invalid selection. " +
                        "Please enter a number between 1 and " + Category.values().length + ".");
            }
        }
    }

    public static Currency parseCurrency(Ui ui) {
        ui.printCurrencyChoice();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            ui.printCurrencyHint();
            String choice = scanner.nextLine();
            if (choice.equals("exit")) {
                return Currency.SGD;
            }
            try {
                int selected = Integer.parseInt(choice);
                if (selected >= 1 && selected <= Currency.values().length) {
                    ui.printCurrencySetting();
                    return Currency.values()[selected - 1]; // Return the selected currency
                }
            } catch (NumberFormatException ignored) {
                System.out.println("Invalid selection. " +
                        "Please enter a number between 1 and " + Category.values().length + ".");
            }
        }
    }


    private static LocalDate parseToLocalDate(String input) throws InvalidCommand {
        if (input == null || input.isBlank()) {
            return null;
        }

        DateTimeFormatter[] formats = new DateTimeFormatter[]{
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd")
        };

        for (DateTimeFormatter format : formats) {
            try {
                return LocalDate.parse(input.trim(), format);
            } catch (DateTimeParseException ignored) {
                throw new InvalidCommand("Invalid date format. Please use yyyy-MM-dd or dd/MM/yyyy.");
            }
        }
        throw new InvalidCommand("Invalid date format. Please use yyyy-MM-dd or dd/MM/yyyy.");
    }
}
