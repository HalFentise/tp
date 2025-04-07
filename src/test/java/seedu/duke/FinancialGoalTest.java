package seedu.duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enums.Currency;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinancialGoalTest {

    private FinancialGoal goal;

    @BeforeEach
    public void setUp() {
        goal = new FinancialGoal("Vacation", 1000.0, "Trip to Japan", Currency.SGD);
    }

    @Test
    public void testConstructorSetsValuesCorrectly() {
        assertEquals("Vacation", goal.getGoal());
        assertEquals(1000.0, goal.getTargetAmount());
        assertEquals("Trip to Japan", goal.getDescription());
        assertFalse(goal.isAchieved());
        assertFalse(goal.isBlank());
    }

    @Test
    public void testSettersUpdateValuesCorrectly() {
        goal.setGoal("New Computer");
        goal.setTargetAmount(2000.0);
        goal.setDescription("Upgrade Full Setup");

        assertEquals("New Computer", goal.getGoal());
        assertEquals(2000.0, goal.getTargetAmount());
        assertEquals("Upgrade Full Setup", goal.getDescription());
        assertFalse(goal.isBlank());
    }

    @Test
    public void testAddToAndSubFromSavings() {
        goal.addToSavings(500.0);
        assertEquals(500.0, goal.getBalance());
        goal.subFromSavings(200.0);
        assertEquals(300.0, goal.getBalance());
    }

    @Test
    public void testForceSetDepositsAndAchievement() {
        goal.forceSetDeposits(999.0);
        assertEquals(999.0, goal.getDeposits());

        goal.forceSetAchieved(true);
        assertTrue(goal.isAchieved());
    }

    @Test
    public void testDefaultConstructorCreatesLooseSavings() {
        FinancialGoal defaultGoal = new FinancialGoal();
        assertEquals("--Loose Savings--", defaultGoal.getGoal());
        assertTrue(defaultGoal.isBlank());
        assertEquals(Integer.MAX_VALUE, defaultGoal.getTargetAmount());
    }

    @Test
    public void testToStringBeforeAndAfterGoalReached() {
        goal.addToSavings(400.0);
        assertTrue(goal.toString().contains("Keep saving!"));

        goal.forceSetDeposits(1500.0);
        goal.checkGoalStatus(); // May rely on Ui.printGoalStatus()
        goal.forceSetAchieved(true); // Bypass UI behavior
        String output2 = goal.toString();
        assertTrue(output2.contains("Goal Reached!"));
    }
}
