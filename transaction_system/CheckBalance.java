package transaction_system;

import auth.AccountManager;

public class CheckBalance {
  public void getBalance(String cardNumber, int pin) {
    AccountManager user = new AccountManager();
    Logger logger = new Logger();

    String userData[] = user.getUser(cardNumber, pin);

    if (userData != null) {
      System.out.println("\n------ ACCOUNT BALANCE ------\n");
      System.out.println("Account Holder    : " + userData[2]);
      System.out.println("Card Number       : " + userData[0]);
      System.out.println("Available Balance : " + userData[3]);
      System.out.println();

      logger.setLog(cardNumber, "BALANCE", null, null, userData[3], "SUCCESS");
    } else {
      System.out.println("Something went wrong!");
      logger.setLog(cardNumber, "BALANCE", null, null, null, "ERROR");
    }
  }
}
