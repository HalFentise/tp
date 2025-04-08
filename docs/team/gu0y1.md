# Project Portfolio Page - Chen Guoyi

## Overview  
This project is a Command-Line based application.  
The main purpose of this product is to manage personal finances.  
Data persistence and security is achieved through storing data locally.

## Summary of Contributions

### Code Contributions  
You can view my code contributions from [here](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=gu0y1)

---

### Saving

**What it does**  
Implements an interactive Saving Mode that allows users to set savings goals, contribute to them, and withdraw funds, all with balance synchronization.

**Implementation**  
* Designed full `SavingMode` interface: `set`, `list`, `contribute`, `deduct`, with retry-on-error logic.
* Simulated saving as expense transactions (`contribute`) and deduction as income (`deduct`), both persisted and marked as completed.
* Verified available balance before contributing, and saving balance before deducting.
* Linked to `TransactionManager` and `Storage` to ensure data integrity and real-time updates.
* Provided visual progress bar and smart status feedback (e.g. "Almost there", "Amazing!") via `printSavingOverview`.

**Justification**  
Provides a structured and user-friendly way to track saving progress, helping users stay motivated toward their financial goals.

---

### Budget

**What it does**  
Allows users to define budget caps per category and track overspending based on completed transactions.

**Implementation**  
* Developed interactive budget creation with inputs for name, amount, end date, and category (`budget set`).
* Only one active budget allowed per category — new setting overrides previous.
* Budget enforced: Completed transactions that exceed cap are blocked if within the end date.
* Supported operations: `list`, `check i/INDEX`, `add`, `modify`, `delete`, with clear error feedback.
* Budgets stored and loaded consistently via `Storage`.

**Justification**  
Encourages mindful spending by tracking per-category limits and warning users before overspending.

---

### Transaction Wizard (Add & Edit)

**What it does**  
Implements guided "wizard-style" interfaces to help users create or modify transactions step by step with error handling.

**Implementation**  
* `AddWizardCommand` prompts for each field with clear formatting and real-time validation.
* Supported fields: `description`, `amount`, `currency`, `category`, `date`, `priority`, `completion`.
* Prevents invalid inputs (e.g. malformed numbers, wrong enum values, invalid dates), and allows cancellation at any point.
* Final confirmation checks if completed transaction will exceed budget (`checkBudgetOverspending`) before saving.
* `EditWizardCommand` allows users to edit any individual field in an existing transaction by ID.

**Justification**  
Greatly improves usability and prevents input errors, making it easier for users to interact with the system confidently.

---

### UI Table Display Enhancements

**What it does**  
Improved readability and visual professionalism of terminal outputs across key features.

**Implementation**  
* Refactored `Ui.printTransactionsTable()` to support:
  - Column auto-alignment
  - Ellipsis truncation for long text fields
  - Scientific notation for extreme numerical values
* Introduced `ConsoleFormatter` to standardize display across all modes and commands.
* Applied formatted display to `list`, `view`, `stats`, `balance`, `goal`, `wizard` interactions.

**Justification**  
Greatly enhances command-line readability, making the app more professional and easier to use.

---

### Documentation

**User Guide**  
* Wrote documentation for `saving` and `budget` commands, including usage examples and edge case behaviors.

**Developer Guide**  
* Explained architecture of `SavingMode`, `BudgetMode`, and `Wizard`-based commands.
* Provided sequence diagrams and class-level documentation to help future developers understand integration logic.

---

### Community

* Refactored `TransactionManager` and `Ui` to support new features
* Reviewed teammates’ PRs and proposed better error messaging and wizard patterns
* Helped define architectural consistency across command modes
* Actively participated in team discussions and integration decisions

