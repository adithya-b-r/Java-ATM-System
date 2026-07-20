package transaction_system;

import auth.AccountManager;

public class Transfer {
  public void transferAmt(String senderCardNum, int pin, String receiverCardNum, long amt) {
    AccountManager user = new AccountManager();
    Logger logger = new Logger();

    String data[] = user.getUser(senderCardNum, pin);
    Long avlBal = Long.parseLong(data[3]);

    boolean isValideRec = user.validCardNumber(receiverCardNum);

    System.out.println();

    if (senderCardNum.equals(receiverCardNum)) {
      System.out.println("Cannot transfer to same account");
    } else if (!isValideRec) {
      System.out.println("Receiver account not found");
    } else if ((avlBal - 100) < amt) {
      System.out.println("Insufficient balance");
    } else {
      user.modifyBalance(senderCardNum, -amt);
      user.modifyBalance(receiverCardNum, amt);

      System.out.println("Processing transfer...");
      System.out.println(amt + " transferred successfully");
      System.out.println();
      System.out.println("Receiver Account: " + receiverCardNum);
      System.out.println("Remaining Balance: " + (avlBal - amt));

      logger.setLog(senderCardNum, "TRANSFER", Long.toString(amt), receiverCardNum, Long.toString(avlBal - amt), "SUCCESS");
      System.out.println();
      return;
    }

    logger.setLog(senderCardNum, "TRANSFER", Long.toString(amt), receiverCardNum, null, "ERROR");
  }
}