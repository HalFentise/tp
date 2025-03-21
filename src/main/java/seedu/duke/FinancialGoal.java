package seedu.duke;

import ui.Ui;

import java.util.Scanner;

public class FinancialGoal {

    private String currentGoal;
    private int targetAmount;
    private String description;

    // Changeable fields

    private boolean isAchieved;
    private int currentAmount;
    private boolean isBlank;

    // Constructors

    public FinancialGoal(String name, int targetAmount, String description) {
        this.currentGoal = name;
        this.targetAmount = targetAmount;
        this.description = description;
        this.isAchieved = false;
        isBlank = false;
    }

    public FinancialGoal() {
        this.currentGoal = "--Loose Savings--";
        this.targetAmount = Integer.MAX_VALUE;
        this.description = "General Savings - Use comamnd to add goal";
        this.isAchieved = false;
        this.currentAmount = 0;
        isBlank = true;
    }

    //get method

    public boolean isAchieved() {
        return this.isAchieved;
    }

    public String getDescription() {
        return this.description;
    }

    public int getTargetAmount() {
        return this.targetAmount;
    }

    public int getCurrentAmount() {
        return this.currentAmount;
    }

    public String getGoal() {
        return this.currentGoal;
    }

    public boolean isBlank() {
        return this.isBlank;
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
        currentAmount += amount;
        checkGoalStatus();
    }

    public void subFromSavings(int amount) {
        isBlank = false;
        currentAmount -= amount;
        Ui.subFromSavings(amount, currentAmount);
    }

    public void checkGoalStatus() {
        isAchieved = Ui.printGoalStatus(currentAmount, targetAmount);
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
                    + currentAmount + " saved \n";
        }
        return currentGoal + "\n"
                + description + "\n$"
                + currentAmount + " / $" + targetAmount + " saved \n"
                 + (isAchieved ? "Goal Reached!" : "Keep saving!");
    }

}