package transaction_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Logger {
  static final String LOG_FILE = "./file_system/transactionsLog.csv";

  public void setLog(String cardNumber, String type, String amount, String receiverCard, String balance,
      String status) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
      String logMsg = LocalDate.now() + "," + cardNumber + "," + type + "," + amount + "," + receiverCard + ","
          + balance + "," + status;

      bw.append(logMsg);
      bw.newLine();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public List<String> getUserLogs(String cardNum) {
    List<String> logs = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE))) {
      String line;

      br.readLine();

      while ((line = br.readLine()) != null) {
        String data[] = line.split(",");

        if (data[1].equals(cardNum)) {
          logs.add(line);
        }
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return logs;
  }
}
