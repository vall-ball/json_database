package server;

import com.google.gson.Gson;

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
    Gson gson = new Gson();

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
                    Command command = gson.fromJson(msg, Command.class);
                    if ("exit".equals(command.type)) {
                        break;
                    }
                    Response response = menu(command);
                    output.writeUTF(gson.toJson(response)); // resend it to the client
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response menu(Command command) {
        switch (command.type) {
            case "get":
                return get(command);
            case "set":
                return set(command);
            case "delete":
                return delete(command);
            default:
                return null;
        }
    }


    public Response get(Command command) {
        Response response = new Response();
        if (!database.map.containsKey(command.key)) {
            response.response = "ERROR";
            response.reason = "No such key";
        } else {
            response.response = "OK";
            response.value = database.get(command.key);
        }
        return response;
    }

    public Response set(Command command) {
        Response response = new Response();
        database.set(command.key, command.value);
        response.response = "OK";
        return response;
    }

    public Response delete(Command command) {
        Response response = new Response();
        if (!database.map.containsKey(command.key)) {
            response.response = "ERROR";
            response.reason = "No such key";
        } else {
            database.delete(command.key);
            response.response = "OK";
        }
        return response;
    }
}

/*
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
 */