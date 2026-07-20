import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

import transaction_system.*;
import auth.AccountManager;

public class ATM {
  public static void main(String[] args) {
    LinkedHashMap<Integer, String> menu = new LinkedHashMap<>();

    AccountManager user = new AccountManager();
    Logger logger = new Logger();

    menu.put(1, "Check Balance");
    menu.put(2, "Withdraw");
    menu.put(3, "Deposit");
    menu.put(4, "Transfer");
    menu.put(5, "Transaction History");
    menu.put(6, "Change Pin");
    menu.put(7, "Exit");

    Scanner sc = new Scanner(System.in);
    int sel = -1;

    System.out.println("Welcome to Java ATM");

    System.out.print("Enter Card Number: ");
    String cardNum = sc.next();

    int pin = -1;
    int limit = 0;

    if (user.validCardNumber(cardNum)) {
      for (int i = 0; i < 3; i++) {
        System.out.print("Enter PIN: ");
        pin = sc.nextInt();

        if (!user.validUser(cardNum, pin)) {
          limit++;

          System.out.println("Incorrect PIN");
          System.out.println("Attempts remaining: " + (3 - limit));
        }else{
          break;
        }
      }

      if (limit == 3) {
        user.blockUser(cardNum);
      }
    }

    if (user.validUser(cardNum, pin)) {
      System.out.println("\nLogin Successful\n");

      while (sel != 7) {
        System.out.println("===== ATM Menu =====");

        for (Map.Entry<Integer, String> entry : menu.entrySet()) {
          System.out.println(entry.getKey() + ". " + entry.getValue());
        }

        System.out.println();
        System.out.print("Enter choice: ");
        sel = sc.nextInt();

        switch (sel) {
          case 1:
            CheckBalance cb = new CheckBalance();
            cb.getBalance(cardNum, pin);
            break;
          case 2:
            Withdraw wd = new Withdraw();
            System.out.print("Enter withdrawal amount: ");
            long wamt = sc.nextLong();
            wd.withdrawAmt(cardNum, pin, wamt);

            System.out.println();
            break;
          case 3:
            Deposit dp = new Deposit();
            System.out.print("Enter deposit amount: ");
            long damt = sc.nextLong();
            dp.depositAmt(cardNum, pin, damt);

            System.out.println();
            break;
          case 4:
            Transfer transfer = new Transfer();
            System.out.print("Enter receiver card number: ");
            String receiver = sc.next();
            System.out.print("Enter transfer amount: ");
            Long amt = sc.nextLong();
            transfer.transferAmt(cardNum, pin, receiver, amt);
            break;
          case 5:
            List<String> logs = logger.getUserLogs(cardNum);

            System.out.println("------------------------------- TRANSACTION HISTORY --------------------------------");
            System.out.println("Timestamp | Card Number |  Operation  |  Amount  | Receiver Card | Balance | Status ");
            System.out.println("------------------------------------------------------------------------------------");

            for (String log : logs) {
              System.out.println(log.replaceAll(",", "      "));
            }
            break;
          case 6:
            System.out.print("Enter current PIN: ");
            int oldPin = sc.nextInt();
            System.out.print("Enter new PIN: ");
            int newPin = sc.nextInt();
            System.out.print("Confirm new PIN: ");
            int newPinC = sc.nextInt();

            if (oldPin != pin) {
              System.out.println("Incorrect current PIN");
            } else if (Integer.toString(newPin).length() != 4) {
              System.out.println("PIN must be 4 digits");
            } else if (newPin != newPinC) {
              System.out.println("New PIN and confirm PIN do not match");
            } else {
              user.changePin(cardNum, oldPin, newPin);
            }
            break;
          case 7:
            System.out.println("Thank you! Exiting the system...");
            break;
          default:
            System.out.println("Invalid selection!");
            break;
        }
      }
    } else if (!user.validCardNumber(cardNum)) {
      System.out.println();
      System.out.println("Account Blocked");
      System.out.println("Please contact your bank");
    } else {
      System.out.println("Invalid credentials!");
    }

    sc.close();
  }
}

/*
 * System.out.println("Enter name: ");
 * System.out.println("Enter balance: ");
 * System.out.println("Enter pin: ");
 * 
 * NewAccount newAcc = new NewAccount("Adithya", 1234, 80000);
 * newAcc.createUser();
 */