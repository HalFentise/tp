# Project Portfolio Page - Peng Ziyi

## Overview
This project is a text application that interact using text line. The Main Purpose of this product is for expense management.
It covers data saving, data parsing and data analysis.

## Summary of Contributions
I implemented six main features in the product: deletion, setting budget limits, notification system, priority tagging, spending alerts, and summary generation.
I also contributed to currency conversion functionalities.

### Code Contributions:
Can view my code contribution from [here](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=Luka&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-02-21&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

---
**Feature Description:**
added seven main functionalities to delete transaction, set budget limit and notifications for transactions:
* Delete Transaction (delete): Lets users remove unwanted or erroneous transaction entries using their index in the displayed list.
* Set Budget Limit (setBudget): Enables users to define a spending cap to avoid overspending. Once the total recorded expenses exceed this limit, a warning is displayed to alert the user.
* Set Notifications for Upcoming Payments (notify): Allows users to schedule reminders for future expenses based on the transaction's description, amount, category, and due date.
* Set Priority (priority): Allows users to assign a priority level (low, medium, or high) to a transaction. By default, all expenses are low priority. This helps users focus on the most urgent or important expenses.
* View Spending Alerts (alert): Displays an overview of upcoming expenses, recurring payments, current remaining budget, and whether the spending has exceeded the set budget limit. This command combines notification, priority, and budget insights in a single view.
* Summary of Expenses (summary): Provides an overview of expenses for a specified time frame, enabling quick and effective financial review.
* Convert Currency (convert): Allows users to convert the amount of a transaction from its original currency to a specified target currency. This is particularly useful for users tracking multi-currency expenses or international purchases.

### Delete

**What it does** <br>
Deletes a transaction from the list by its index.

**Implementation** <br>
The system reads the `delete` command followed by an index. It checks if the index is valid using the helper method `checkIdEmpty(id)` and removes the transaction from the list using `transactions.remove(id)`.

**Justification** <br>
Allows users to correct input mistakes or remove outdated transactions from the record.

---

### Set Budget

**What it does** <br>
Sets a total spending limit for the user. A warning will be shown if the total expenses exceed this limit.

**Implementation** <br>
The `set budget` command takes an integer as the budget limit. The system iterates over all transactions (excluding deleted ones), calculates the total amount spent, and compares it with the budget limit. If the limit is exceeded, a warning message is printed.

**Justification** <br>
Helps users maintain financial discipline by preventing overspending.

---

### Notify

**What it does** <br>
Sets reminders for upcoming transactions based on the description, category, and due date.

**Implementation** <br>
The `notify` command captures four components: `description`, `amount`, `category`, and `date`. It searches the existing transactions and updates the due date of the matching entry using `LocalDate.parse(date)` and sets it via `transaction.setDate()`.

**Justification** <br>
Allows users to track and plan for future payments such as bills or subscriptions.

---

### Set Priority

**What it does** <br>
Assigns a priority level (LOW, MEDIUM, HIGH) to a transaction.

**Implementation** <br>
The `set priority` command takes in a transaction index and a priority string. The system converts the string into an enum using `Priority.valueOf(...)` and updates the transaction. The UI then confirms the change with a printout.

**Justification** <br>
Enables users to focus on more important or urgent expenses in their list.

---

### Alert

**What it does** <br>
Provides a summary of budget status, upcoming payments, and high-priority expenses.

**Implementation** <br>
When the `alert` command is issued, the system performs three main tasks:
1. Lists upcoming due transactions (notifications).
2. Displays transactions with MEDIUM or HIGH priorities.
3. Shows recurring expenses using `Ui.printRecurringTransactions()`.

**Justification** <br>
Gives users a consolidated view of financial urgency and budget health to support better decision-making.

---

### Summary

**What it does** <br>
Prints an overview of transactions and total spending within a given date range.

**Implementation** <br>
The `summary` command accepts `/from` and `/to` date inputs. The system filters transactions between those dates and computes the total expenditure using Java streams. The summary is printed with transaction details and total.

**Justification** <br>
Allows users to reflect on their past financial behavior and adjust future budgeting plans.

---

### Convert

**What it does** <br>
Converts the currency of a specific transaction into a different target currency.

**Implementation** <br>
The `convert` command accepts a transaction index and a target currency code. It locates the corresponding transaction, retrieves the original currency and amount, and performs the conversion using `transaction.convertTo(targetCurrency)`. The UI displays both the original and converted values.

**Justification** <br>
Supports users managing multi-currency expenses, particularly useful for international transactions.

---

**Design Consideration:**

The `deletion`, `budget limit` and `notification` features are designed to improve financial awareness and discipline.
Users can monitor their spending relative to a predefined threshold and receive timely reminders for future payments.

Priority levels help users organize and focus on transactions based on importance or urgency.  
Spending alerts consolidate relevant financial insights—notifications, recurring transactions, and budget status—into
one unified interface for quick decision-making.

Expense Summaries: The summary feature provides users with quick, insightful overviews of their spending patterns
within a selected time frame, enhancing their ability to review and adjust financial behavior promptly.

The convert feature adds a layer of flexibility for users dealing with multiple currencies. It simplifies financial tracking by converting values into a uniform currency for easier comparison and understanding. It is designed with immediate feedback through the UI to help users understand how currency changes affect their recorded transactions.
The conversion is performed using the transaction's stored data and is integrated directly into the TransactionManager, ensuring that currency changes do not interfere with other transaction attributes or analytical features.

These capabilities integrate seamlessly with the transaction management system, enhancing the user experience through automation
and clear visual cues for overspending or pending transactions.

---

### Enhancements implemented:
For the budget feature, logic enhancement ensures users verify current budget conditions before adding transactions.
Users receive immediate feedback if a transaction exceeds the budget limit, encouraging proactive adjustments.

Refined the budget check logic to verify constraints before allowing new transaction additions.

Enhanced real-time feedback for transactions exceeding budget limits.

Improved resilience of the notify and priority commands through better error handling and clearer CLI prompts.

### Contributions to the UG:
Documented command formats, parameters, and examples for all features I implemented (delete, setbudget, notify, priority, alert, summary, convert).

Structured the command section for better user readability and consistent format.

Helped ensure that V2.0 features were clearly communicated and easy for end-users to understand and use.

### Contributions to the DG:
Authored the V2.0 user stories related to budgeting, alerts, priorities, and summaries.

Added descriptions, design considerations, and sequence diagrams for the features seven functionalities I developed.

Helped maintain the issue trackers, ensuring consistency with implementation goals and long-term usability of the product.

### Community:
* Maintain `ui`,`parser`and `transactionManager`
* Review teams code and fix the bug
* Maintain issue tracker based on the PE dry run feedback
* Integrated `Convert Currency` new functionality with current other features. 