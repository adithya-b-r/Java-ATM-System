# ATM System in Java

A console-based Automated Teller Machine (ATM) system built in Java. It uses CSV files as a lightweight persistent database for storing account details and transaction history.

## Features

- **Card Authentication & Safety**:
  - Validates card numbers.
  - Limits login attempts to 3; blocks the card/account on the 3rd failed attempt.
- **Transactions**:
  - **Check Balance**: View current available balance.
  - **Withdraw**: Withdraw cash in multiples of 100 (minimum 100, maximum 10,000 per transaction).
  - **Deposit**: Deposit cash into the account.
  - **Transfer**: Transfer funds securely to another active card number.
- **Transaction History**: Displays detailed logs of all past transactions (timestamp, card number, operation type, amount, receiver, new balance, and status).
- **Security**: Allows users to change their 4-digit PIN securely after verifying the current PIN.
- **Daily Withdrawal Limit**: Restricts daily withdrawals to a maximum of 50,000.

## Project Structure

```text
ATM_System/
├── ATM.java                     # Main entry point of the application
├── auth/
│   └── User.java                # Manages credentials, sessions, and account state modifications
├── transaction_system/
│   ├── CheckBalance.java        # Balance inquiry logic
│   ├── Deposit.java             # Cash deposit logic
│   ├── Logger.java              # Manages writing/reading transaction logs
│   ├── Transfer.java            # Account-to-account funds transfer logic
│   └── Withdraw.java            # Cash withdrawal logic with business rule checks
└── file_system/
    ├── accounts.csv             # Database for user accounts
    └── transactionsLog.csv      # Database for transaction history
```

## Database Schema (CSV Files)

### accounts.csv
Stores account credentials, balances, and status.
`cardNumber,pin,name,balance,status`

### transactionsLog.csv
Logs all transaction attempts and results.
`timestamp,cardNumber,type,amount,receiverCard,balance,status`

## Execution Instructions

Ensure Java JDK (version 8 or above) is installed.

### 1. Compile the Project
From the root directory (`ATM_System`), compile all Java source files:
```bash
javac ATM.java auth/User.java transaction_system/*.java
```

### 2. Run the Application
Start the console application:
```bash
java ATM
```
