package transaction_system;

import auth.AccountManager;

public class Deposit {
  public void depositAmt(String cardNum, int pin, long amt) {
    AccountManager user = new AccountManager();
    Logger logger = new Logger();

    String data[] = user.getUser(cardNum, pin);
    long avlBal = Long.parseLong(data[3]);

    if (amt < 100) {
      System.out.println("Deposit must be at least 100");
    } else if (amt > 100000) {
      System.out.println("Maximum deposit allowed is 100000");
    } else {
      user.modifyBalance(cardNum, amt);
      System.out.println("Processing deposit...");
      System.out.println(amt + " deposited successfully");
      System.out.println("New Balance: " + (avlBal + amt));
      logger.setLog(cardNum, "DEPOSIT", Long.toString(amt), null, Long.toString(avlBal + amt), "SUCCESS");

      return;
    }

    logger.setLog(cardNum, "DEPOSIT", Long.toString(amt), null, null, "ERROR");
  }
}
