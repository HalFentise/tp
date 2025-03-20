package seedu.duke;

import java.util.Scanner;

public class Duke {
    private TransactionManager transactions = new TransactionManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Praser praser = new Praser();
        System.out.println("Welcome to Duke");
        while (true) {
            String input = scanner.nextLine();
        }
    }
}
