# Exploratory Testing Charters – Monefy App

## CHARTER-001: Transaction Entry

- **Explore:** Adding expenses and income  
- **Using:** New transactions  
- **Check:**  
  - How easy it is to add, edit, or delete transactions  
  - Inputs: amount, notes, category  
  - Edge values (very high/low)  
  - Validation for missing or invalid inputs  


## CHARTER-002: Account Management

- **Explore:** Creating and managing accounts  
- **Using:** A new account  
- **Check:**  
  - Creating, renaming, and deleting accounts  
  - Naming rules  
  - Behavior when modifying accounts in use  


## CHARTER-003: Security Features

- **Explore:** App lock and unlock mechanisms  
- **Using:** PIN, Face ID, or Touch ID  
- **Check:**  
  - Lock behavior on app relaunch  
  - Unlock functionality  
  - Any security bypass issues  


## Findings

**Testing device:** iPhone 15 Pro Max

**iOS Version:** iOS 18.4

Screenshots can be found in [task1-test-screenshots](task1-test-screenshots) 

## Bug Report

| Bug ID   | Description | Charter ID   | Severity | Pre-condition                                                                 | Steps to Reproduce                                                                                          | Expected Result                                                   | Actual Result                                                                         | Comment                                                                                                      |
|----------|--------------|----------|--------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------|----------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------|--|
| BUG-001  | Missing confirmation prompt about transaction deletion. | CHARTER-001  | High     | User is on the expense or income edit page.                                   | 1. Tap the bin icon in the top-right corner.                                                                | App prompts the user for confirmation before deletion.            | The record is deleted immediately.                                                    | Users might accidentally delete the wrong record. Limited mobile display increases risk of data loss.        |
| BUG-002  | Incorrect account label | CHARTER-002  | Low      | User is on the overflow menu.                                                 | 1. Tap the "Accounts" option.                                                                                | A list of accounts is displayed with a correct header/title.      | An "Add" label is shown on the left but does nothing.                                 | Compared to other menus, the label should be meaningful and positioned consistently.                         |
| BUG-003  | Update of decimal places for an account does not take immediate effect. | CHARTER-002  | Medium   | 1. Main currency is set to 3 decimal places.  <br>2. User is on add account page. | 1. Fill in mandatory fields. <br>2. Set exchange rate decimal places to 2. <br>3. Tap "Done". <br>4. Enter a balance with 3 decimal places. <br>5. Tap "Add". | User should enter balance using 2 decimal places as configured.   | User can enter 3 decimal places, following main currency settings instead of local setting. | Changing decimal places in the account also affects the global currency setting. Should this be account-specific? |



## Suggestions & Improvements

> As a new user, I found it a bit confusing to learn the app due to some UI/UX inconsistencies.

| Bug ID  | Charter ID   | Description                                                                                                   | Suggestion                                                                                                              |
|---------|--------------|---------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------|
| UI-001  | CHARTER-001  | On the Add/Edit Expenses page, there's an account dropdown to the left of the amount field. Its design isn't consistent with other dropdowns in the app. | Improve the UI of the dropdown to make it more intuitive and consistent across the app.                                |
| UI-002  | CHARTER-002  | On the Add Account page, when selecting a currency, the "Back" button (top left) actually exits the entire Add Account screen. | Add a prompt to warn users about unsaved data, or redesign the dropdown so the "Back" button clearly relates to currency selection only. |



## Charter Prioritization

| Charter ID   | Title              | Priority     | Reason                                                                                      |
|--------------|--------------------|--------------|---------------------------------------------------------------------------------------------|
| CHARTER-001  | Transaction Entry  | **High**   | Core functionality of the app. Users must be able to add/edit transactions reliably.       |
| CHARTER-002  | Account Management | **Medium** | Important for organizing finances but not used as frequently as transactions.              |
| CHARTER-003  | Security Features  | **Low**    | Optional features. Issues here don’t prevent use of the main app features.                 |


## Risk Assessment & Mitigation

### 1. Data Security & Privacy

| **Risk** | **Mitigation** |
|----------|----------------|
| **Weak authentication** (e.g. ineffective PIN, Face/Touch ID) may allow unauthorized access | Validate all lock/unlock mechanisms, test biometric fallbacks, and simulate app relaunch/timeout scenarios |
| **Unencrypted data at rest** (e.g. SQLite, shared prefs) may expose sensitive information | Confirm encryption of local data and secure storage of credentials and tokens |



### 2. Data Integrity & Accuracy

| **Risk** | **Mitigation** |
|----------|----------------|
| **Incorrect balance calculation** due to rounding, decimal mismatch, or sync conflicts | Validate transaction math, currency conversions, and balance updates in various edge cases |
| **Loss or duplication of data** during sync or restore | Test backup/sync under flaky networks; simulate app uninstall/reinstall scenarios |


### 3. Usability & UX Confusion

| **Risk** | **Mitigation** |
|----------|----------------|
| **Hidden or unclear UI elements** (e.g. account dropdown beside amount) may confuse users | Conduct exploratory testing as a new user; verify layout, icons, and control placement across screen sizes |
| **Navigation is inconsistent or misleading** | Validate that all buttons behave consistently (e.g., back buttons in nested modals or dropdowns) and don't unexpectedly exit screens |

