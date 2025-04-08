# Project Portfolio Page - Zhu Yangyi

## Overview
This project is a Command-Line based application. <br>
The main purpose of this product is to manage personal finances. <br>
Data persistence and security is achieved through storing data locally.

## Summary of Contributions
### Code Contributions: 
#### Can view my code contribution from [here](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=yangyi-zhu&breakdown=true)  

**Core Features** <br>
I implemented the following core features of this application:
* Search
* Recur
* Edit
* Goal

---

### Search

**What it does** <br>
Allows users to search transactions by search term (default) or ID

**Implementation** <br>
The system reads the search command prefix followed by the search term. Should the search term begin with the prefix 
`id-`, the search returns the transaction with the specified ID.

**Justification** <br>
Allows users to quickly locate specific records without having to manually browse through a potentially long list.

---

### Recur

**What it does** <br>
Set transactions to recur every X days. (i.e. subscriptions)

**Implementation** <br>
The recurrence period of a transaction is stored in the variable `recurringPeriod`. <br>
Transactions are considered recurring if `recurringPeriod` is positive. <br>
<br>
Recurring transactions have their values up to the current date summed up to be reflected in progress towards goal. <br>
They are also used in alerts where the next billing date is shown for upcoming payments.

**Justification** <br>
Allows users to record subscriptions.

---

### Edit

**What it does** <br>
Updates information stored in a `transaction` object.

**Implementation** <br>
Reads edit commands and uses switch-case to choose amongst different tasks. <br>
Information that can be edited with this feature include `description`, `category`, `amount`, and `currency`. <br>
Edit commands with no or unrecognized attributes return an error message.

**Justification** <br>
Allows users to update information without having to delete and recreate when an error is made.

---

### Goal

**What it does** <br>
Track progress towards savings goals. <br>
It includes the following features:
* Create new goal
* Check goal status
* Print goal
* Edit goal title
* Edit goal description
* Edit goal target

**Implementation** <br>
Reads goal commands and uses switch-case to choose amongst different tasks. <br>
Goal commands with no or unrecognized attributes default to printing the goal or creating new goal if no goal exists. <br>
There may only be one goal at a time. Creating a new goal replaces the current if it exists.

**Justification** <br>
Allow users to stay focused on a single goal. <br>
Options to edit goal information allow the user to amend any errors instead of having to create a new goal.

---

### Other Enhancements
* Added reminder for upcoming recurring transactions at start.

---

### Documentation
**Comments**
* Added JavaDoc comments for own methods and FinancialGoal class. <br>

**README**
* Added introduction and lightly formatted. <br>

**User Guide:**
* Added documentation and testing instructions for features implemented by me.
* Review and clean up errors present in other commands.
* Ensure consistency in formatting. <br>

**Developer Guide:**
* Document features implemented (`recur`, `edit`, `search`, `goal`)
* Added sequence diagrams for core features implemented
* Added sequence diagram for recurring transaction reminder
* Documented FinancialGoal class
* Added diagram for FinancialGoal class
* Added sequence diagrams for goal commands and goal creation
* Updated testing instructions
* Updated table of contents

---

### Community
* Maintain `Ui`,`Parser`and `TransactionManager`
* Review teams code and fix bugs
* Maintain issue tracker
* Integrate `FinancialGoal` class (base data structure drafted by `Faheem Akram`)
