package seedu.duke;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Praser {
    public static void praser(String input, TransactionManager transactions) {
        try {
            String[] parts = input.toLowerCase().split(" ");
            if (parts.length <= 1) {
                throw new IllegalArgumentException("Invalid input");
            }

            switch (parts[0]) {
                case "add":
                case "addtag":
                case "removetag":
                case "tick":
                    transactions.tickTransaction(Integer.parseInt(parts[1]));
                    break;
                case "untick":
                    transactions.unTickTransaction(Integer.parseInt(parts[1]));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
