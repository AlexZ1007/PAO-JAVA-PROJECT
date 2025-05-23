package Audit;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Audit {
    private static final String filename = "log/audit_log.csv";

    public static void logAction(String action) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write(action + "," + timestamp + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
