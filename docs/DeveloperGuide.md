# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

### 1. Transaction Basic Data Structure

**Feature Description:**  
Pang Zixi implemented the `Transaction` class as the basic data structure for transactions. It includes the following fields:
- `id` (Transaction ID)
- `description` (Transaction description)
- `amount` (Transaction amount)
- `currency` (Currency type)
- `category` (Transaction category)
- `date` (Transaction date)
- `status` (Transaction status)

```java
public class Transaction {
    private int id;
    private String description;
    private int amount;
    private Currency currency;
    private Category category;
    private LocalDate date;
    private Status status;
    
    // Constructor and getter/setter methods
}
```

**Design Consideration:**  
This data structure provides all the essential information required for a transaction,
and it supports modifying and querying the transaction status (e.g., Pending, Completed).

### Transaction Management Features: Mark, Add, Exit
**Feature Description:**  
I added several functionalities to manage transactions:
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
These functionalities allow the program to provide basic transaction management capabilities.
Searching and adding transactions is streamlined for ease of use, and the exit process is handled to ensure data persistence.

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
