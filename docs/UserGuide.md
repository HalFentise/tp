# User Guide

## Introduction

{Give a product intro}

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features 

{Give detailed description of each feature}

### Adding an expense: `add`
Adds an expense entry to the budget tracker.

Format: `add d/DESCRIPTION a/AMOUNT c/CATEGORY [t/TAG]`

* `DESCRIPTION` refers to the name of the expense.
* `AMOUNT` is the cost of the expense.
* `CATEGORY` is the category of spending (e.g., food, transport, entertainment).
* `TAG` (optional) can be used for additional labeling.

Example of usage: 

`add d/Dinner a/15.50 c/Food`  
`add d/Netflix Subscription a/12.99 c/Entertainment t/Monthly`

### Listing all expenses: `list`
Displays all recorded expenses.

Format: `list`

### Editing an expense: `edit`
Edits an existing expense entry.

Format: `edit INDEX [d/DESCRIPTION] [a/AMOUNT] [c/CATEGORY] [t/TAG]`

* `INDEX` refers to the position in the displayed expense list.
* At least one optional field must be provided.

Examples:

* `edit 1 a/20.00 (Changes the amount of the 1st expense to 20.00)`
* `edit 2 d/Lunch a/10.00 c/Food (Updates description, amount, and category)`

### Deleting an expense: `delete`
Deletes the specified expense entry.

Format: `delete INDEX`
* `INDEX` refers to the position in the displayed expense list.
Examples:
* `delete 2` (Deletes the 2nd expense in the list)

### Setting a budget limit: `setBudget`
Sets a spending limit to prevent overspending.

Format: `setBudget a/AMOUNT`
* `AMOUNT` is the maximum spending limit.
Example:
* `setBudget a/500` (Sets a spending cap of 500)

### Searching for expenses: `search`
Finds expenses based on description or category.

Format: `search KEYWORD`

Examples:
* `search Food` (Lists all expenses categorized under Food)
* `search Netflix` (Finds expenses with "Netflix" in the description)

### Tracking recurring transactions: `recurring`
Creates a recurring expense entry.

Format: `recurring d/DESCRIPTION a/AMOUNT c/CATEGORY f/FREQUENCY`
* `FREQUENCY` can be daily, weekly, or monthly.
Example:
* `recurring d/Gym Membership a/50 c/Fitness f/Monthly`

### Setting financial goals: `goal`
Set a savings goal.

Format: `goal d/DESCRIPTION a/AMOUNT`
Example:
* `goal d/New Laptop a/1000`

### Ticking off expenditures: `tick`
Marks an expense as paid or completed.

Format: `tick INDEX`
Example:
* `tick 3 (Marks the 3rd expense as completed)`

### Setting notifications for upcoming payments: `notify`
Sets reminders for upcoming expenses.

Format: `notify d/DESCRIPTION a/AMOUNT c/CATEGORY t/DATE`
Example:
* `notify d/Rent a/1000 c/Housing t/2025-03-01 (Sets a reminder for rent payment on March 1st, 2025)`

### Prioritizing specific expenses: `priority`
Marks an expense as a high-priority item.

Format: `priority INDEX`
Example:
* `priority 1` (Marks the 1st expense as high priority)

### Exiting the program: `exit`
Closes the application.

Format: `exit`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
