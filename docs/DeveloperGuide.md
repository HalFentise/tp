# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the
original source as well}

## Design & implementation

### 1. Transaction Basic Data Structure

**Feature Description:**  
`Pang Zixi` implemented the `Transaction` class as the basic data structure for transactions. It includes the following
fields:

- `id` (Transaction ID)
- `description` (Transaction description)
- `amount` (Transaction amount)
- `currency` (Currency type)
- `category` (Transaction category)
- `date` (Transaction date)
- `status` (Transaction status)

`Zhu Yangyi` added the ability to set the period at which expenses recur, stored in the `recurringPeriod` field.

```java
public class Transaction {
    private int id;
    private String description;
    private int amount;
    private Currency currency;
    private Category category;
    private LocalDate date;
    private Status status;
    private int recurringPeriod;

    // Constructor and getter/setter methods
}
```

**Design Consideration:**  
This data structure provides all the essential information required for a transaction, <br>
and it supports modifying and querying the transaction status (e.g., Pending, Completed).

### Transaction Management Features: Mark, Add, Exit

**Feature Description:**  
`Pang Zixi` added several functionalities to manage transactions:
* Mark Transaction: Allows users to mark a transaction with a specific status (Completed or Pending).
* Add Transaction: Users can add a new transaction with description, amount, category, and other attributes.
* Exit Program: Exits the program and ensures any unsaved transactions are stored.

```angular2html
public void addTransaction(int id, String description, int amount, Category category) {
    Transaction transaction = new Transaction(id, description, amount, Currency.USD, category, LocalDate.now(), Status.PENDING);
    transactions.add(transaction);
}

public void searchTransaction(int id) {
    transactions.stream().filter(t -> t.getId() == id).forEach(t -> System.out.println(t));
}
```

**Design Consideration:**  
These functionalities allow the program to provide basic transaction management capabilities. <br>
Searching and adding transactions is streamlined for ease of use, and the exit process is handled to ensure data
persistence.

---

### Transaction Management Features: Delete, Set Budget Limit and Notifications

**Feature Description:**  
`Peng Ziyi` added three main functionalities to delete transaction, set budget limit and notifications for transactions:
* Delete Transaction (delete): Lets users remove unwanted or erroneous transaction entries using their index in the displayed list.
* Set Budget Limit (setBudget): Enables users to define a spending cap to avoid overspending. Once the total recorded expenses exceed this limit, a warning is displayed to alert the user.
* Set Notifications for Upcoming Payments (notify): Allows users to schedule reminders for future expenses based on the transaction's description, amount, category, and due date.

```angular2html
/**
     * Deletes a transaction from the transaction list.
     *
     * @param id the index of the transaction to be removed.
     */
    public void deleteExpense(int id) {
        if (checkIdEmpty(id)) {
            return;
        }
        transactions.remove(id);
    }

/** 
    *function to record and trace the total budget limit
    */
    public void checkBudgetLimit(int budgetLimit) {
        int totalAmount = 0;
        for (Transaction transaction : transactions) {
            if (!transaction.isDeleted()) {
                totalAmount += transaction.getAmount();
            }
        }
        if (totalAmount > budgetLimit) {
            System.out.println("Warning: You have exceeded your budget limit!");
        }
    }
    
/** 
    *Sets a notification for an upcoming transaction
    */
    public void notify(String description, int amount, String categoryString, String date) {
        LocalDate dueDate = LocalDate.parse(date);

        Category category = Category.valueOf(categoryString);

        for (Transaction transaction : transactions) {
            if (transaction.getDescription().equals(description) && transaction.getCategory().equals(category)) {
                transaction.setDate(dueDate);
            }
        }
    }
```

**Design Consideration:**  


The deletion, budget limit and notification features are designed to improve financial awareness and discipline.
Users can monitor their spending relative to a predefined threshold and receive timely reminders for future payments.

These capabilities integrate seamlessly with the transaction management system, enhancing the user experience through automation
and clear visual cues for overspending or pending transactions.
---

`Zhu Yangyi` added the following functionalities to manage transactions:

* Set Recurring Period: Allows users to set transactions to recur every `recurringPeriod` days.
* Search Transaction: Allows users to search through list of transactions by either description (default) or id.
* Edit Transaction: Allows users to edit the description, category, amount, or currency of a transaction.

```java
public void setRecurringPeriod(int recurringPeriod) {
    this.recurringPeriod = recurringPeriod;
}

public ArrayList<Transaction> searchTransactionList(boolean isIndex, String searchTerm, Ui ui) {
    try {
        ArrayList<Transaction> printTransactions = new ArrayList<>();
        if (isIndex) {
            // Searches for transaction with given index and adds to printTransactions
        } else {
            // Searches for all transactions whose description contain the search term and adds to printTransactions
        }
        return printTransactions;
    } catch (Exception e) {
        // Error handling
    }
}

public void editInfo(int id, T info) {
    if (checkIdEmpty(id)) {
        return;
    }
    transactions.get(id).setInfo(info);
}
```

**Design Consideration:**  
The ability to set recurring period allows users to manage subscriptions or bills without having to add them
repeatedly. <br>
`searchTransactionList(boolean, String, Ui)` and `editInfo(int, T)` enable users to browse and make changes to their log
with ease.

---

### 2. Goal:

**Feature Description:**
`Zhu Yangyi` integrated the `Goal` class into the program through the `Parser` class.

```java
public static void parseGoalCommands(String command, Ui ui, FinancialGoal goal) throws Exception {
    switch (command) {
    case GOAL_TARGET:
        // Command to update target
    case GOAL_DESC:
        // Command to update description
    case GOAL_TITLE:
        // Command to update title
    case GOAL_STATUS:
        // Command to view status
    case GOAL_NEW:
        // Command to create a new goal
    default:
        // View goal (default) / create new goal (if empty)
    }
}
```

**Design Consideration:**  
This implementation allows users to update individual parts of the goal, allowing for a more modular approach when only
minor modifications are required compared to having to set a new goal each time.

---

## Product scope

### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

| Version | As a ... | I want to ...             | So that I can ...                                           |
|---------|----------|---------------------------|-------------------------------------------------------------|
| v1.0    | new user | see usage instructions    | refer to them when I forget how to use the application      |
| v2.0    | user     | find a to-do item by name | locate a to-do without having to go through the entire list |

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
