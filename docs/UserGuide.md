# User Guide 

## Introduction

**NoteUrSavings** is a lightweight, user-friendly budgeting tool designed to help users effectively track their
expenses, manage recurring payments, set financial goals, and stay within budget. Built with simplicity in mind, this
Java-based application allows users to easily add, edit, and view their expenses using concise commands. With features
such as budget limits, customizable categories, currency settings, reminders, and savings goals, NoteUrSavings empowers
users to take control of their personal finances in a structured yet flexible manner. Whether you're managing daily
spending or saving up for long-term goals, NoteUrSavings provides all the essential tools through a clean command-line
interface.

## Quick Start

1. Ensure that you have Java 17 or above installed.
1. Down the latest version of `NoteUrSavings.jar` from [here](http://link.to/duke).

> (Optional) Ideally, relocate the `.jar` file to somewhere easy to find first.

3. Click on the `.jar` file to run.

> If the above does not work, right-click the file and select `Copy as path`. <br>
> Open CMD and run `java -jar [path you just copied]`.

## Features

### Adding an expense: `add`

Adds an expense entry to the budget tracker.

Format: `add d/DESCRIPTION a/AMOUNT c/CATEGORY [t/DATE]`

* `DESCRIPTION` refers to the name of the expense.
* `AMOUNT` is the cost of the expense.
* `CATEGORY` is the category of spending (e.g., food, transport, entertainment).
* `DATE` (optional) is your expense date, support the following format:
    * `dd-mm-yyyy`
    * `dd/mm/yyyy`
    * `yyyy-mm-dd`
    * `yyyy/mm/dd`

Examples:

`add d/Dinner a/15.50 c/Food`  
`add d/Netflix Subscription a/12.99 c/Entertainment t/04-06-2025`

#### Wizard Mode

To enter step-by-step mode, simply type: `add`

You will be guided to input: 

- Description
- Amount
- Currency
- Category
- Date
- Priority (LOW / MEDIUM / HIGH)
- Completion status (YES / NO)

> Budget checks and warnings are **only triggered if the transaction is marked as completed**.

> If the transaction exceeds the allocated budget or falls before the budget end date, warnings will be shown. However, the transaction is still recorded.

#### Completion Logic

- Only **completed** transactions are considered for:
  - Account balance calculation
  - Savings tracking
  - Budget spending evaluation

- **Incomplete** transactions are treated as drafts or pending, and excluded from all calculations.

---


### Listing all expenses: `list`

Displays all recorded expenses.

Format: `list`

---

### Editing an expense: `edit`

Edits an existing expense entry.

Format: `edit [tag] [id] [value]` <br>
Tags: `desc`, `cat`, `am`, `curr`

* `desc`: Task description
* `cat`: Expense category
* `am`: Amount to pay
* `curr`: Currency

Examples:

* `edit desc 1 50mm flathead screws` (Sets description of expense `1` to `50mm flathead screws`)
* `edit cat 2 OTHER` (Sets category of expense `2` to `OTHER`)
* `edit am 3 25` (Sets amount to pay of expense `3` to `25`)
* `edit curr 4 JPY` (Sets currency of expense `4` to Japanese Yen)

#### Wizard Mode

To start editing interactively: `edit`

You will be guided to:

- Select a transaction ID
- Choose the field you want to update
- Enter the new value

**Editable Fields in Wizard Mode:**

1. Description  
2. Amount  
3. Currency  
4. Category  
5. Date (`yyyy-mm-dd`)  
6. Priority (`LOW`, `MEDIUM`, `HIGH`)

> You can type `cancel` at any step to exit the wizard.

---

#### Important Notes

- Editing the amount, category, or status of a transaction may affect balance, budget limits, and savings.
- Budgets only apply to **completed** transactions.
- Changing the transaction date may also impact budget rules if it falls within an active budget range.

---

### Deleting an expense: `delete`

Deletes the specified expense entry.

Format: `delete INDEX`

* `INDEX` refers to the position in the displayed expense list.

Example:

* `delete 2` (Deletes the 2nd expense in the list)

---

### Clearing all expenses: `clear`

Remove all recorded expenses.

Format: `clear`

---

### Setting a budget limit: `setbudget`

Sets a spending limit to prevent overspending.

Format: `setbudget a/AMOUNT`

* `AMOUNT` is the maximum spending limit.

Example:

* `setbudget a/500` (Sets a spending cap of 500)

---

### Searching for expenses: `search`

Finds expenses based on description or category.

Format: `search [keyword]`, `search id-[id]`

Examples:

* `search Netflix` (Finds expenses with `Netflix` in the description)
* `search id-5` (Finds the expense with id `5`)

---

### Tracking recurring transactions: `recurring`

Creates a recurring expense entry.

Format: `recur [id]/[period]`

* `[period]` refers to how often this payment is made in days.

Example:

* `recur 1/7` (Sets expense with id `1` to recur weekly)

---

### Setting financial goals: `goal`

Set a savings goal.

Format: `goal [tag] [value]` <br>
Tags: `target`, `desc`, `title`, `status`, `new`

* `target`: Update target balance to save up for.
* `desc`: Update goal description.
* `title`: Update goal title.
* `status`: Check progress towards goal.
* `new`: Create new goal.

> By default, goal commands without a recognized tag print the current goal. <br>
> If there is not a goal yet, `goal new` is run instead. <br>

Example:

* `goal target 1000` (Updates target to 1000)
* `goal desc New phone and earpieces` (Updates description to `New phone and earpieces`)
* `goal title Gadgets` (Updates title to `Gadgets`)
* `goal status` (Prints progress towards goal)
* `goal new` (Creates new goal)
* `goal` (Creates new goal if no goal exists, prints goal information otherwise)

---

### Ticking off expenditures: `tick`

Marks an expense as paid or completed.

Format: `tick INDEX` <br><br>
Example:

* `tick 3` (Marks the 3rd expense as completed)

---

### Undo Tick off: `untick`

If you tick one expense but wish to undo, use `untick`.

Format: `untick INDEX` <br><br>
Example:

* `untick 3` (Marks the 3rd expense as not completed)

---

### Change default currency: `currency`

Change default currency of the program.

Format: `currency`

---

### Viewing a summary of expenses: `summary`

Viewing a summary of expenses for a selected time frame.

Format: `summary from/START_DATE to/END_DATE` <br><br>
Example:

* `summary from/2025-03-02 to/2025-03-29` (Displays a summary of expenses for the selected time period)

---

### Setting notifications for upcoming payments: `notify`

Sets reminders for upcoming expenses.

Format: `notify d/DESCRIPTION c/CATEGORY t/DATE` <br><br>
Example:

* `notify d/Dinner c/Food t/2025-03-01` (Sets a reminder for dinner payment on March 1st, 2025)

---

### Prioritizing specific expenses: `priority`

Marks an expense based on different priorities, default set to be low.

Format: `priority INDEX priority_level` <br><br>
Example:

* `priority 1 high` (Marks the 1st expense as high priority)

---

### Setting alerts for higher spending: `alert`

Showing the current budget limit, upcoming transactions and the high priority transactions,
the recurring transaction list respectively.

Format: `alert` <br><br>
Example: <br>
![My Diagram](Images/Alert_demo.png)

---

### Exiting the program: `exit`

Closes the application.

Format: `exit`

---

### Managing a Savings Goal: `saving`

The `saving` command lets you manage your personal savings goal within a dedicated interactive mode. You can set a target, contribute funds, withdraw funds, and check your savings progress.

#### Entering Saving Mode

To enter Saving Mode: `saving`

You will see a prompt like this: `saving>`

Type `exit` at any time to return to the main menu.

#### Available Saving Mode Commands

| Command | Format | Description |
|--------|--------|-------------|
| `set` | `set` | Create a new savings goal interactively |
| `list` | `list` | View your current savings goal, progress, and target |
| `contribute` | `contribute a/AMOUNT` | Add funds from your account balance into savings |
| `save` | `save a/AMOUNT` | Alias for `contribute` |
| `deduct` | `deduct a/AMOUNT` | Withdraw funds from savings back to your account |
| `help` | `help` | View all saving commands |
| `exit` | `exit` | Return to main menu |

#### Rules and Logic

- You **must set a savings goal** before you can contribute or deduct.
- Contributions will **reduce your current balance** by the given amount.
- Deductions will **return the amount to your balance**.
- Only completed transactions are recorded as savings actions.
- Savings are stored as transactions with the `SAVING` category and are visible in your full transaction list.

#### Notes

- If your balance is insufficient during `contribute`, the action will be rejected.
- If your savings are insufficient during `deduct`, the action will also be rejected.
- All saving operations are recorded as transactions and affect your balance and goal progress.

---

### Managing Budgets: `budget`

The `budget` command lets you manage per-category spending limits through an interactive mode. You can create, view, and modify budgets for different expense categories. Each category can only have **one active budget**, which limits the total **completed expenses** before a specified end date.


#### Entering Budget Mode

To enter Budget Mode: `budget`

You will enter a dedicated mode: `budget>`

Type `exit` at any time to return to the main menu.


#### Budget Mode Commands

| Command | Format | Description |
|--------|--------|-------------|
| `set` | `set` | Create a new budget interactively |
| `list` | `list` | View all budgets and their remaining amount |
| `check` | `check i/INDEX` | View details of a specific budget |
| `add` | `add i/INDEX a/AMOUNT` | Increase the budget amount |
| `modify` | `modify i/INDEX [n/NAME] [a/AMOUNT] [e/DATE] [c/CATEGORY]` | Edit fields of a budget |
| `delete` | `delete i/INDEX` | Remove a budget |
| `help` | `help` | View all budget commands |
| `exit` | `exit` | Return to the main menu |


#### Budget Rules and Enforcement

- A **budget applies only to one category**. If you create a new budget for the same category, it will overwrite the previous one.
- **Only completed transactions** are counted toward budget spending.
- **Transactions dated before or on the budget's end date** will be blocked if they cause the budget to be exceeded.
- Transactions dated **after the end date** are not restricted by that budget.
- Budget usage is updated in real time. Overspending warnings are shown when a transaction is marked as complete.

#### Notes

- Budget limits are enforced only when a transaction is marked as **completed**.
- A real-time "remaining" amount is calculated by subtracting the completed spending from the total.
- When you exceed your budget, a warning will be printed during `tick` or when completing a transaction via wizard.


## FAQ

**Q**: How do I transfer my data to another computer?

**A**: NoteUrSavings creates a `/data` directory at the address at which the program itself is stored. 
Transfer everything in this directory and to another computer and all data should load accordingly.

## Enumerations
| Type     | Supported Variations                                                                                                  |
|----------|-----------------------------------------------------------------------------------------------------------------------|
| Category | `EDUCATION`, `ENTERTAINMENT`, `FOOD`, `GROCERIES`, `HEALTH`, `HEALTHCARE`, `HOUSING`, `OTHER`, `SHOPPING`, `TRANSPORT`|
| Currency | `CNY`, `EUR`, `GBP`, `JPY`, `SGD`, `USD`                                                                              |
| Priority | `HIGH`, `MEDIUM`, `LOW`                                                                                               |


## Command Overview

| Command     | Format                                           | Description                                                                                         | Example                                                                                           |
|-------------|--------------------------------------------------|-----------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------|
| `add`       | `add d/DESCRIPTION a/AMOUNT c/CATEGORY [t/DATE]` | Adds a new expense.                                                                                 | `add d/Dinner a/15.50 c/Food`<br>`add d/Netflix Subscription a/12.99 c/Entertainment t/04-06-2025` |
| `list`      | `list`                                           | Lists all recorded expenses.                                                                        | `list`                                                                                            |
| `edit`      | `edit [tag] [id] [value]`                        | Edits an expense's description (`desc`), amount (`am`), category (`cat`), or currency (`curr`).     | `edit desc 1 Coffee`<br>`edit cat 2 OTHER`<br>`edit am 3 25`<br>`edit curr 4 JPY`                 |
| `delete`    | `delete INDEX`                                   | Deletes the expense at the given index.                                                             | `delete 2`                                                                                        |
| `clear`     | `clear`                                          | Deletes **all** expenses.                                                                           | `clear`                                                                                           |
| `setbudget` | `setbudget a/AMOUNT`                             | Sets a maximum spending cap.                                                                        | `setbudget a/500`                                                                                 |
| `search`    | `search [keyword]`<br>`search id-[id]`           | Searches by keyword or expense ID.                                                                  | `search Netflix`<br>`search id-5`                                                                 |
| `recur`     | `recur [id]/[period]`                            | Sets a recurring expense in days.                                                                   | `recur 1/7` (weekly)                                                                              |
| `goal`      | `goal [tag] [value]`                             | Manages savings goals. Tags: `target`, `desc`, `title`, `status`, `new`                             | `goal target 1000`<br>`goal desc New phone`<br>`goal title Gadgets`<br>`goal status`<br>`goal new` |
| `tick`      | `tick INDEX`                                     | Marks an expense as completed/paid.                                                                 | `tick 3`                                                                                          |
| `untick`    | `untick INDEX`                                   | Unmarks a previously ticked expense.                                                                | `untick 3`                                                                                        |
| `currency`  | `currency`                                       | Changes the default currency.                                                                       | `currency`                                                                                        |
| `notify`    | `notify d/DESCRIPTION c/CATEGORY t/DATE`         | Sets a notification for future payment.                                                             | `notify d/Dinner c/Food t/2025-03-01`                                                             |
| `priority`  | `priority INDEX priority_level`                  | Sets priority level of an expense. Levels: `low`, `medium`, `high`.                                 | `priority 1 high`                                                                                 |
| `summary`   | `summary`                                        | Shows all the list for the given time frame.                                                        | `summary`                                                                                         |
| `exit`      | `exit`                                           | Exits the program.                                                                                  | `exit`                                                                                            |
| `alert`     | `alert`                                          | Triggers a warning for higher spending.                                                             | `alert`                                                                                           |

## Need Help?

Make sure you entered the commands exactly as specified, with correct spacing and slashes.
Still stuck? Contact support or check our GitHub Discussions.
