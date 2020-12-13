package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
    String address = "127.0.0.1";
    int port = 23456;
    Database database = new Database();

    public void work() throws UnknownHostException {
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            System.out.println("Server started!");
            while (true) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                ) {
                    String msg = input.readUTF(); // reading a message
                    if ("exit".equals(msg.split(" ")[0])) {
                        break;
                    }
                    output.writeUTF(menu(msg)); // resend it to the client
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String menu(String msg) {
        String[] command = msg.split(" ");
        switch (command[0]) {
            case "get":
                return get(command);
            case "set":
                return set(command);
            case "delete":
                return delete(command);
            default:
                return "";
        }
    }


    public String get(String[] command) {
        int number = Integer.parseInt(command[1]);
        if (number > 1000 || "".equals(database.get(number)) || database.get(number) == null) {
            return "ERROR";
        } else {
            return database.get(number);
        }
    }

    public String set(String[] command) {
        int number = Integer.parseInt(command[1]);
        if (number > 1000) {
            return "ERROR";
        } else {
            String value = CommandParser.text(command);
            database.set(number, value);
            return "OK";
        }
    }

    public String delete(String[] command) {
        int number = Integer.parseInt(command[1]);
        if (number > 1000) {
            return "ERROR";
        } else {
            database.set(number, "");
            return "OK";
        }
    }
}