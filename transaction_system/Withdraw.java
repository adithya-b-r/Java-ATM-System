package transaction_system;

import auth.AccountManager;

public class Withdraw {
  public void withdrawAmt(String cardNumber, int pin, long amt) {
    AccountManager user = new AccountManager();
    Logger logger = new Logger();

    String data[] = user.getUser(cardNumber, pin);
    long avlBal = Long.parseLong(data[3]);

    if(user.dailyWithdrawLimit(cardNumber)){
      System.out.println("Daily withdrawal limit exceeded 50000");
    }else if (amt < 100) {
      System.out.println("Minimum withdrawal is 100");
    } else if (amt % 100 != 0) {
      System.out.println("Amount must be multiples of 100");
    } else if (amt > 10000) {
      System.out.println("Maximum withdrawal per transaction is 10000");
    } else {
      if ((avlBal - 100) < amt) {
        System.out.println("Insufficient Balance");
      } else {
        user.modifyBalance(cardNumber, -amt);

        System.out.println("Processing withdrawal...");
        System.out.println(amt + " withdrawn successfully");
        System.out.println("Remaining Balance: " + (avlBal - amt));

        logger.setLog(cardNumber, "WITHDRAW", Long.toString(amt), null, Long.toString(avlBal - amt), "SUCCESS");
        return;
      }
    }

    logger.setLog(cardNumber, "WITHDRAW", Long.toString(amt), null, null, "ERROR");
  }
}
