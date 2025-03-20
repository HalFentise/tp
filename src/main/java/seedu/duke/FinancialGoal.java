package seedu.duke;

import java.util.Scanner;

public class FinancialGoal {

    private String currentGoal;
    private int targetAmount;
    private String description;

    // Changeable fields

    private boolean isAchieved;
    private int currentAmount;

    // Constructors

    public FinancialGoal(String name, int targetAmount, String description) {
        this.currentGoal = name;
        this.targetAmount = targetAmount;
        this.description = description;
        this.isAchieved = false;
    }

    public FinancialGoal() {
        this.currentGoal = "--Loose Savings--";
        this.targetAmount = Integer.MAX_VALUE;
        this.description = "General Savings - Use comamnd to add goal";
        this.isAchieved = false;
        this.currentAmount = 0;
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

    // set method

    private void setDescription(String description) {
        this.description = description;
    }

    private void setTargetAmount(int targetAmount) {
        this.targetAmount = targetAmount;
    }

    private void setGoal(String goal) {
        this.currentGoal = goal;
    }

    // Saving methods

    public void addToSavings(int amount) {
        currentAmount += amount;
        checkGoalStatus();
    }

    public void subFromSavings(int amount) {
        currentAmount -= amount;
        if (currentAmount < 0){
            System.out.println("Warning. You have gone into negative.");
        }
    }

    public void checkGoalStatus() {
        if (currentAmount >= targetAmount) {
            isAchieved = true;
            System.out.println("You have achieved the goal! Congratulations!");
        } else {
            System.out.println("You're " + currentAmount +
                    " out of " + targetAmount + ". Good luck!");
        }
    }

    // Goal setting

    public FinancialGoal setGoal(){
        Scanner sc = new Scanner(System.in);
        int amount;
        System.out.print("Want to set a new goal (Y/N)? ");

        if (!sc.nextLine().equals("Y")) {
            return this;
        }
        System.out.println("Name of new goal:");
        setGoal(sc.nextLine());
        System.out.println("Target amount of new goal:");
        amount = Integer.parseInt(sc.nextLine());
        setTargetAmount(amount);
        System.out.println("Description of new goal:");
        setDescription(sc.nextLine());
        System.out.println("New goal created! \n" + this);
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