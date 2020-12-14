package server;

import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

public class Worker implements Runnable{

    FileHandler fileHandler = new FileHandler("D:\\programming\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json");
    Gson gson = new Gson();
    Server server;
    Command command;
    Response response;
    DataOutputStream output;
    ReadWriteLock readWriteLock;

    public Worker(Command command, Server server, DataOutputStream output, ReadWriteLock readWriteLock) {
        this.server = server;
        this.command = command;
        this.output = output;
        this.readWriteLock = readWriteLock;
    }

    public void work() throws IOException {
        //System.out.println("WORK!!! ");
        System.out.println("command.type = " + command.type);
        if ("exit".equals(command.type)) {
            response = new Response();
            response.response = "OK";
            output.writeUTF(gson.toJson(response));
            server.flag = false;
            //System.out.println(server.flag);
        }
        response = menu(command);
        output.writeUTF(gson.toJson(response));
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
        readWriteLock.readLock().lock();
        Map<String, String> map = fileHandler.load();
        if (!map.containsKey(command.key)) {
            response.response = "ERROR";
            response.reason = "No such key";
        } else {
            response.response = "OK";
            response.value = map.get(command.key);
        }
        readWriteLock.readLock().unlock();
        return response;
    }

    public Response set(Command command) throws IOException {
        readWriteLock.writeLock().lock();
        Response response = new Response();
        Map<String, String> map = fileHandler.load();
        map.put(command.key, command.value);
        response.response = "OK";
        fileHandler.save(map);
        readWriteLock.writeLock().unlock();
        return response;
    }

    public Response delete(Command command) throws IOException {
        Response response = new Response();
        readWriteLock.writeLock().lock();
        Map<String, String> map = fileHandler.load();
        if (!map.containsKey(command.key)) {
            response.response = "ERROR";
            response.reason = "No such key";
        } else {
            map.remove(command.key);
            response.response = "OK";
        }
        fileHandler.save(map);
        readWriteLock.writeLock().unlock();
        return response;
    }

    @Override
    public void run() {
        System.out.println("RUN!!! ");
        try {
            work();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
