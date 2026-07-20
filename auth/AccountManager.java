package auth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;

import transaction_system.Logger;

public class AccountManager {
  private static final String FILE_PATH = "./file_system/accounts.csv";

  public boolean validUser(String cardNumber, int pin) {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
      String line;

      br.readLine();

      while ((line = br.readLine()) != null) {
        String data[] = line.split(",");
        String cardNum = data[0];
        int upin = Integer.parseInt(data[1]);
        String isActive = data[4];

        if (cardNum.equals(cardNumber) && pin == upin && isActive.equals("ACTIVE")) {
          return true;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }

    return false;
  }

  public String[] getUser(String cardNumber, int pin) {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
      String line;

      br.readLine();

      while ((line = br.readLine()) != null) {
        String data[] = line.split(",");

        if (data[0].equals(cardNumber) && data[1].equals(Integer.toString(pin))
            && data[data.length - 1].equals("ACTIVE")) {
          return data;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }

    return null;
  }

  public boolean validCardNumber(String cardNumber) {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
      String line;

      br.readLine();

      while ((line = br.readLine()) != null) {
        String data[] = line.split(",");

        if (data[0].equals(cardNumber) && data[data.length - 1].equals("ACTIVE")) {
          return true;
        }
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }

    return false;
  }

  private String getLastEntry() {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
      String line;
      String lastLine = null;

      br.readLine();

      while ((line = br.readLine()) != null) {
        lastLine = line;
      }

      return lastLine;
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }

    return null;
  }

  public boolean saveUser(String name, int pin, long balance) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
      String lastLine = getLastEntry();

      if (lastLine == null)
        return false;

      String lastEntry[] = lastLine.split(",");
      String cardNumber = Long.toString(Long.parseLong(lastEntry[0]) + 1);

      String newEntry = cardNumber + "," + Integer.toString(pin) + "," + name + "," + Long.toString(balance) + ","
          + "ACTIVE";

      bw.write(newEntry);
      bw.newLine();

      System.out.println("Account Created Successfully");
      System.out.println("Your card number is: " + cardNumber);
      System.out.println("Please remember your PIN");

      return true;
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }

    return false;
  }

  public void modifyBalance(String cardNumber, long amt) {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
      BufferedWriter bw = new BufferedWriter(new FileWriter("./file_system/temp.csv"));

      String line;

      br.readLine();

      while ((line = br.readLine()) != null) {
        String data[] = line.split(",");

        if (data[0].equals(cardNumber)) {
          Long newBal = Long.parseLong(data[3]) + amt;
          data[3] = Long.toString(newBal);
          line = data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4];
          bw.append(line);
        } else {
          bw.append(line);
        }

        bw.newLine();
      }

      bw.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    try (BufferedReader br = new BufferedReader(new FileReader("./file_system/temp.csv"))) {
      BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH));

      String line = "cardNumber,pin,name,balance,status";
      bw.append(line);
      bw.newLine();

      while ((line = br.readLine()) != null) {
        bw.append(line);
        bw.newLine();
      }

      bw.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    File tempFile = new File("./file_system/temp.csv");
    tempFile.delete();
  }

  public void blockUser(String cardNum) {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
      BufferedWriter bw = new BufferedWriter(new FileWriter("./file_system/temp.csv"));
      String line;

      br.readLine();

      while ((line = br.readLine()) != null) {
        String data[] = line.split(",");

        if (data[0].equals(cardNum)) {
          data[4] = "BLOCKED";
          line = data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4];
        }

        bw.append(line);
        bw.newLine();
      }

      bw.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    try (BufferedReader br = new BufferedReader(new FileReader("./file_system/temp.csv"))) {
      BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH));

      String line = "cardNumber,pin,name,balance,status";
      bw.append(line);
      bw.newLine();

      while ((line = br.readLine()) != null) {
        bw.append(line);
        bw.newLine();
      }

      bw.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    File file = new File("./file_system/temp.csv");
    file.delete();
  }

  public void changePin(String cardNum, int oldPin, int newPin) {
    Logger logger = new Logger();

    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
      BufferedWriter bw = new BufferedWriter(new FileWriter("./file_system/temp.csv"));
      String line;

      br.readLine();

      while ((line = br.readLine()) != null) {
        String data[] = line.split(",");

        if (data[0].equals(cardNum) && Integer.parseInt(data[1]) == oldPin) {
          data[1] = Integer.toString(newPin);
          line = data[0] + "," + data[1] + "," + data[2] + "," + data[3] + "," + data[4];
        }

        bw.append(line);
        bw.newLine();
      }

      bw.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.setLog(cardNum, "CHANGE_PIN", null, null, null, "ERROR");

      return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader("./file_system/temp.csv"))) {
      BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH));

      String line = "cardNumber,pin,name,balance,status";
      bw.append(line);
      bw.newLine();

      while ((line = br.readLine()) != null) {
        bw.append(line);
        bw.newLine();
      }

      bw.close();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      logger.setLog(cardNum, "CHANGE_PIN", null, null, null, "ERROR");

      return;
    }

    File file = new File("./file_system/temp.csv");
    file.delete();

    logger.setLog(cardNum, "CHANGE_PIN", null, null, null, "SUCCESS");

    System.out.println("\nPIN changed successfully");
    System.out.println();
  }

  public boolean dailyWithdrawLimit(String cardNum) {
    String fileName = "./file_system/transactionsLog.csv";
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      long amt = 0;
      String line;

      br.readLine();

      while ((line = br.readLine()) != null) {
        String data[] = line.split(",");

        if (data[1].contains(cardNum) && data[2].contains("WITHDRAW") && data[0].contains(LocalDate.now().toString()) && data[6].contains("SUCCESS")) {
          amt += Long.parseLong(data[3]);
        }
      }

      if (amt >= 50000) {
        return true;
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }

    return false;
  }
}