package seedu.duke;

import enums.Currency;
import ui.Ui;

import java.util.Scanner;

/**
 * Represents a financial goal with a target amount, description, and current savings balance.
 * Allows setting and updating goal properties, tracking savings and expenses, and interacting with the user.
 */
public class FinancialGoal {
    private double deposits;
    private String currentGoal;
    private double targetAmount;
    private String description;
    private Currency currency;

    // Changeable fields

    private boolean isAchieved;
    private boolean isBlank;
    private double expenses;

    // Constructors

    /**
     * Constructs a financial goal with the specified name, target amount, and description.
     *
     * @param name The title of the goal.
     * @param targetAmount The target amount to be saved.
     * @param description A description of the goal.
     */
    public FinancialGoal(String name, double targetAmount, String description) {
        this.currentGoal = name;
        this.targetAmount = targetAmount;
        this.description = description;
        this.isAchieved = false;
        isBlank = false;
        this.currency = Currency.SGD;
    }

    /**
     * Constructs a financial goal with the specified name, target amount, description, and currency.
     *
     * @param name The name/title of the goal.
     * @param targetAmount The target amount to be saved.
     * @param description A description of the goal.
     * @param currency The currency used for the goal.
     */
    public FinancialGoal(String name, double targetAmount, String description, Currency currency) {
        this.currentGoal = name;
        this.targetAmount = targetAmount;
        this.description = description;
        this.isAchieved = false;
        isBlank = false;
        this.currency = currency;
    }

    /**
     * Constructs a default goal representing general savings.
     */
    public FinancialGoal() {
        this.currentGoal = "--Loose Savings--";
        this.targetAmount = Integer.MAX_VALUE;
        this.description = "General Savings - Use command to add goal";
        this.isAchieved = false;
        this.deposits = 0;
        isBlank = true;
    }

    // Get methods

    /**
     * Returns whether the goal has been achieved.
     *
     * @return True if the goal is achieved, false otherwise.
     */
    public boolean isAchieved() {
        return this.isAchieved;
    }

    /**
     * Gets the description of the goal.
     *
     * @return The goal's description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the target amount of the goal.
     *
     * @return The goal's target amount.
     */
    public double getTargetAmount() {
        return this.targetAmount;
    }

    /**
     * Gets the name/title of the goal.
     *
     * @return The goal's name.
     */
    public String getGoal() {
        return this.currentGoal;
    }

    /**
     * Returns whether the goal is blank/uninitialized.
     *
     * @return True if the goal is blank, false otherwise.
     */
    public boolean isBlank() {
        return this.isBlank;
    }

    /**
     * Gets the current balance, calculated as deposits minus expenses.
     *
     * @return The current balance saved toward the goal.
     */
    public double getBalance() {
        return this.deposits - this.expenses;
    }

    /**
     * Gets the current deposits.
     *
     * @return Total sum of deposits made.
     */
    public double getDeposits() {
        return this.deposits;
    }

    // Set methods

    /**
     * Sets the description of the goal and marks it as initialized.
     *
     * @param description The new description for the goal.
     */
    public void setDescription(String description) {
        isBlank = false;
        this.description = description;
    }

    /**
     * Sets the target amount of the goal and marks it as initialized.
     *
     * @param targetAmount The new target amount for the goal.
     */
    public void setTargetAmount(double targetAmount) {
        isBlank = false;
        this.targetAmount = targetAmount;
    }

    /**
     * Sets the name/title of the goal and marks it as initialized.
     *
     * @param goal The new name for the goal.
     */
    public void setGoal(String goal) {
        isBlank = false;
        this.currentGoal = goal;
    }

    // Saving methods

    /**
     * Adds a specified amount to the goal's savings and checks if the goal has been achieved.
     *
     * @param amount The amount to add to savings.
     */
    public void addToSavings(double amount) {
        isBlank = false;
        deposits += amount;
        checkGoalStatus();
    }

    /**
     * Subtracts a specified amount from the goal's savings and updates the balance in the UI.
     *
     * @param amount The amount to subtract from savings.
     */
    public void subFromSavings(double amount) {
        isBlank = false;
        deposits -= amount;
        Ui.subFromSavings(amount, getBalance());
    }

    /**
     * Checks if the savings have met or exceeded the target amount and updates the achievement status.
     */
    public void checkGoalStatus() {
        isAchieved = Ui.printGoalStatus(getBalance(), targetAmount);
    }

    /**
     * Updates the total expenses used for calculating the balance from the transaction manager.
     *
     * @param transactions The transaction manager instance containing expense data.
     */
    public void updateExpenses(TransactionManager transactions) {
        expenses = transactions.getTotalAmount();
    }

    // Goal setting (uses ui parameter)

    /**
     * Prompts the user to create a new financial goal by entering the name, target amount, and description.
     *
     * @param ui The UI instance for interacting with the user.
     */
    public void createNewGoal(Ui ui) {
        Scanner sc = new Scanner(System.in);
        String goalName;
        double amount;
        Ui.createGoalConfirm();

        if (!sc.nextLine().equalsIgnoreCase("Y")) {
            Ui.createGoalAborted();
            return;
        }

        Ui.createGoalName();
        goalName = sc.nextLine();

        Ui.createGoalTarget();
        try {
            amount = Double.parseDouble(sc.nextLine());
            if (amount <= 0) {
                ui.showError("Target amount must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            ui.showError("Invalid target amount. Please enter a valid number.");
            return;
        }

        Ui.createGoalDescription();
        setDescription(sc.nextLine());
        setGoal(goalName);
        setTargetAmount(amount);

        Ui.createGoalSuccess();
    }

    // To String method

    /**
     * Returns a formatted string representing the goal's details, balance, and status.
     *
     * @return A string summarizing the goal's current state.
     */
    @Override
    public String toString() {
        if (targetAmount == Integer.MAX_VALUE) {
            return currentGoal + "\n"
                    + description + "\n$"
                    + "balance: " + getBalance() + "\n";
        }
        return currentGoal + "\n"
                + description + "\n$"
                + getBalance() + " / $" + targetAmount + " saved \n"
                + (isAchieved ? "Goal Reached!" : "Keep saving!");
    }

    /**
     * Sets the deposits manually (for testing or forced updates).
     *
     * @param deposits The new deposit amount to set.
     */
    public void forceSetDeposits(double deposits) {
        this.deposits = deposits;
    }

    /**
     * Sets the achievement status manually (for testing or forced updates).
     *
     * @param isAchieved Whether the goal should be marked as achieved.
     */
    public void forceSetAchieved(boolean isAchieved) {
        this.isAchieved = isAchieved;
    }

}
