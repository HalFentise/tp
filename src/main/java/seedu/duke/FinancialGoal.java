package seedu.duke;

import enumStructure.Currency;
import ui.Ui;
import java.util.Scanner;

public class FinancialGoal {
    private double deposits;
    private String currentGoal;
    private double targetAmount;
    private String description;
    private Currency currency;

    // Changeable fields

    private boolean isAchieved;
    private double currentAmount;
    private boolean isBlank;
    private double expenses;

    // Constructors

    public FinancialGoal(String name, double targetAmount, String description) {
        this.currentGoal = name;
        this.targetAmount = targetAmount;
        this.description = description;
        this.isAchieved = false;
        isBlank = false;
        this.currency = Currency.SGD;
    }

    public FinancialGoal(String name, double targetAmount, String description, Currency currency) {
        this.currentGoal = name;
        this.targetAmount = targetAmount;
        this.description = description;
        this.isAchieved = false;
        isBlank = false;
        this.currency = currency;
    }

    public FinancialGoal() {
        this.currentGoal = "--Loose Savings--";
        this.targetAmount = Integer.MAX_VALUE;
        this.description = "General Savings - Use command to add goal";
        this.isAchieved = false;
        this.deposits = 0;
        isBlank = true;
    }

    //get method

    public boolean isAchieved() {
        return this.isAchieved;
    }

    public String getDescription() {
        return this.description;
    }

    public double getTargetAmount() {
        return this.targetAmount;
    }

    public double getCurrentAmount() {
        return this.currentAmount;
    }

    public String getGoal() {
        return this.currentGoal;
    }

    public boolean isBlank() {
        return this.isBlank;
    }

    public double getExpenses() {
        return this.expenses;
    }

    public double getBalance() {
        return this.expenses - this.deposits;
    }

    // set method

    public void setDescription(String description) {
        isBlank = false;
        this.description = description;
    }

    public void setTargetAmount(int targetAmount) {
        isBlank = false;
        this.targetAmount = targetAmount;
    }

    public void setGoal(String goal) {
        isBlank = false;
        this.currentGoal = goal;
    }

    // Saving methods

    public void addToSavings(int amount) {
        isBlank = false;
        deposits += amount;
        checkGoalStatus();
    }

    public void subFromSavings(double amount) {
        isBlank = false;
        deposits -= amount;
        Ui.subFromSavings(amount, getBalance());
    }

    public void checkGoalStatus() {
        isAchieved = Ui.printGoalStatus(getBalance(), targetAmount);
    }

    public void updateExpenses(TransactionManager transactions) {
        expenses = transactions.getTotalAmount();
    }

    // Goal setting

    public FinancialGoal createNewGoal() {
        Scanner sc = new Scanner(System.in);
        int amount;
        Ui.createGoalConfirm();

        if (!sc.nextLine().equals("Y")) {
            Ui.createGoalAborted();
            return this;
        }
        Ui.createGoalName();
        setGoal(sc.nextLine());
        Ui.createGoalTarget();
        amount = Integer.parseInt(sc.nextLine());
        setTargetAmount(amount);
        Ui.createGoalDescription();
        setDescription(sc.nextLine());
        Ui.createGoalSuccess();
        return this;
    }

    // To String method

    @Override
    public String toString() {
        if (targetAmount == Integer.MAX_VALUE){
            return currentGoal + "\n"
                    + description + "\n$"
                    + "balance: " + getBalance() + "\n";
        }
        return currentGoal + "\n"
                + description + "\n$"
                + getBalance() + " / $" + targetAmount + " saved \n"
                 + (isAchieved ? "Goal Reached!" : "Keep saving!");
    }
}
