package server;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server {
    String address = "127.0.0.1";
    int port = 23456;
    //Database database = new Database();
    FileHandler fileHandler = new FileHandler("D:\\programming\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json");
    Gson gson = new Gson();
    ServerSocket server;
    boolean flag = true;

    public void work() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            server = serverSocket;
            System.out.println("Server started!");
            ExecutorService executor = Executors.newFixedThreadPool(4);
            ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
            while (flag) {
                try (
                        Socket socket = server.accept();
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                ) {
                    String msg = input.readUTF(); // reading a message
                    Command command = gson.fromJson(msg, Command.class);
                    executor.submit(new Worker(command, this, output,reentrantReadWriteLock));
                    Thread.sleep(50);
                    System.out.println("flag = " + flag);
                 if (!flag) {
                     Response response = new Response();
                     response.response = "OK";
                     output.writeUTF(gson.toJson(response));
                     server.close();
                     executor.shutdown();
                     break;
                 }
                    //output.writeUTF(gson.toJson(worker.response)); // resend it to the client
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            server.close();
            e.printStackTrace();
        }
        finally {
            server.close();
        }
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

////////////////////////////////////
/*
 String address = "127.0.0.1";
    int port = 23456;
    //Database database = new Database();
    FileHandler fileHandler = new FileHandler("D:\\programming\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json");
    Gson gson = new Gson();
    ServerSocket server;

    public void work() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(address))) {
            server = serverSocket;
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
                        Response response = new Response();
                        response.response = "OK";
                        output.writeUTF(gson.toJson(response));
                        server.close();
                        break;
                    }
                    Response response = menu(command);
                    output.writeUTF(gson.toJson(response)); // resend it to the client
                }
            }
        } catch (IOException e) {
            server.close();
            e.printStackTrace();
        }
        finally {
            server.close();
        }
    }

    public Response menu(Command command) throws IOException {
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


    public Response get(Command command) throws FileNotFoundException {
        Response response = new Response();
        Map<String, String> map = fileHandler.load();
        if (!map.containsKey(command.key)) {
            response.response = "ERROR";
            response.reason = "No such key";
        } else {
            response.response = "OK";
            response.value = map.get(command.key);
        }
        return response;
    }

    public Response set(Command command) throws IOException {
        Response response = new Response();
        Map<String, String> map = fileHandler.load();
        map.put(command.key, command.value);
        response.response = "OK";
        fileHandler.save(map);
        return response;
    }

    public Response delete(Command command) throws IOException {
        Response response = new Response();
        Map<String, String> map = fileHandler.load();
        if (!map.containsKey(command.key)) {
            response.response = "ERROR";
            response.reason = "No such key";
        } else {
            map.remove(command.key);
            response.response = "OK";
        }
        fileHandler.save(map);
        return response;
    }
}
 */