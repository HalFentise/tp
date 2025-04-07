# Project Portfolio Page 

## Overview
This project is a text application that interact using text line. The Main Purpose of this product is for expense management.
It covers data saving, data parsing and data analysis.

## Summary of Contributions
### Code Contributions: 
#### Can view my code contribution from [here](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=halfentise&breakdown=true)  

**Data Structure:** Implemented the Transaction class as the fundamental data structure for handling transactions.

* **What it does:** The `Transaction` class encapsulates the key fields related to a transaction, including transaction ID, description, amount, currency type, category, date, and status.
This structure helps in managing and organizing the transaction details effectively. Additionally, the `TransactionManager` class provides higher-level management,
enabling operations like adding, removing, and modifying transactions.

* **Justification:** This data structure aims to provide a clear and efficient way to store transaction details, supporting the functionality of the application in managing and tracking financial data.
By structuring the transactions with these fields, it allows for effective querying, updating, and maintaining financial records, which is essential for the user to monitor their financial activities.

* **Highlights:**

    * The `Transaction` class includes crucial details such as the ID, description, amount, currency, category, date, and status of the transaction.

    * The related `enum` structures for `Currency`, `Category`, and `Status` enhance the flexibility and clarity of managing transaction data. For example,
  the `Currency` enum provides a clear mapping of currency types (USD, EUR, GBP, etc.) with corresponding exchange rates.

    * The `TransactionManager` class is responsible for managing these transactions at a higher level, enabling actions like adding and searching transactions while maintaining the integrity of transaction records.

**Data Saving:**  
Implemented the Storage class for managing data saving and loading operations.

* **What it does:** The `Storage` class provides the ability to persist transaction data to a CSV file and load it back when needed. It ensures that the transaction data is safely stored on disk and can be retrieved after the application restarts. The class handles the creation of necessary folders, saving transactions in a CSV format, and parsing the CSV data back into `Transaction` objects.

* **Justification:** Storing transaction data is crucial for maintaining a permanent record of all financial activities. By using a CSV file, the application ensures that data is easy to store, read, and maintain, while remaining lightweight and platform-independent. This feature helps users preserve their financial records across sessions, which is vital for long-term tracking and analysis.

* **Highlights:**

    * Data Folder Creation: Automatically checks and creates the required folder for storing transaction data if it doesn't exist.

    * Saving Transactions: Transaction data is saved into a CSV file with details like id, description, amount, currency, category, date, and status.

    * Loading Transactions: The class reads from the CSV file and reconstructs the transaction objects, ensuring the application has access to all previous transaction records.

    * Error Handling: Proper error handling ensures the application remains stable even if there is an issue with the file I/O or the data format.

    * Data Integrity: The class verifies that all fields are correctly formatted and ensures the integrity of each transaction by checking for null values or missing fields before processing.


**Core Features:**
I implemented several key features such as add, list, clear, help, tick, and untick to handle basic transaction management functionalities.

* **What it does:**
These features are integral to the management of transactions, allowing users to add new transactions, list all transactions, clear all transactions, toggle the completion status of transactions, and get help on using the application. These features are triggered by specific user commands and are implemented in both the `parser` and `ui` classes.

* **Justification:**
These features form the core functionality of the application, allowing users to easily interact with and manage their transaction data. They make it easier for users to add new records, view existing ones, clear data when needed, and manage the status of transactions, all through simple CLI commands. These functionalities are essential for a smooth user experience and the effective management of finances.

* **Highlights:**

    * Add: Allows users to add new transactions by specifying the description, amount, and category. It helps users quickly enter new expenses or income into the system.

    * List: Displays all existing transactions, making it easy to review the records and check the current status of all transactions.

    * Clear: Clears all transaction data, providing a fresh start if needed. This feature is useful for users who want to reset the application’s state.

    * Help: Provides users with a comprehensive list of available commands and how to use them, ensuring a user-friendly experience.

    * Tick/Untick: These commands allow users to mark transactions as completed or uncompleted. This is useful for tracking the progress of recurring transactions or keeping track of pending tasks.

### Enhancements implemented:
* Fixed the conversion issue caused by decimal values in amounts.
* Improved the functionality for adding and deleting transactions, ensuring better performance and reliability.
### Documentation
* **User Guide:**
  * Added documentation and instructions of my related code and features.
  * Added command overview and introduction in UG
  * Maintain UG to meet our product implementation
* **Developer Guide:**
  * Added Table of Content, Getting Started, Product Scope, User Stories v1.0 and Non-Functional Requirements
  * Added Design part
  * Added related features in Implementation part 
  * Added data structure and `add` `tick` `untick` `list` introduction
### Community:
* Maintain `ui`,`parser`and `transactionManager`
* Review teams code and fix the bug
* Maintain issue tracker
* Fix CL errors
