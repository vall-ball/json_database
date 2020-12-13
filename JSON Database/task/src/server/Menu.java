package server;

import java.util.Scanner;

public class Menu {
    Database database = new Database();
    private static final Scanner scanner = new Scanner(System.in);

    public void process() {
        while (true) {
            String[] command = scanner.nextLine().split(" ");

            switch (command[0]) {
                case "get":
                    get(command);
                    break;
                case "set":
                    set(command);
                    break;
                case "delete":
                    delete(command);
                    break;
                case "exit":
                    return;
            }
        }
    }

    public void get(String[] command) {
        int number = Integer.parseInt(command[1]);
        if (number > 1000 || "".equals(database.get(number)) || database.get(number) == null) {
            System.out.println("ERROR");
        } else {
            System.out.println(database.get(number));
        }
    }

    public void set(String[] command) {
        int number = Integer.parseInt(command[1]);
        if (number > 1000) {
            System.out.println("ERROR");
        } else {
            String value = CommandParser.text(command);
            database.set(number, value);
            System.out.println("OK");
        }
    }

    public void delete(String[] command) {
        int number = Integer.parseInt(command[1]);
        if (number > 1000) {
            System.out.println("ERROR");
        } else {
            database.set(number, "");
            System.out.println("OK");
        }
    }
}
