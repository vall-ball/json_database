package client;

import com.google.gson.Gson;
import server.FileHandler;

import java.io.FileNotFoundException;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws UnknownHostException, FileNotFoundException, InterruptedException {
        ArgsParser parser = new ArgsParser();
        Gson gson = new Gson();
        if ("-in".equals(args[0])) {
            FileHandler fileHandler = new FileHandler("D:\\programming\\JSON Database\\JSON Database\\task\\src\\client\\data\\" + args[1]);
            String s = fileHandler.loadString();
            Client client = new Client(s);
            client.work();
        } else {
            Client client = new Client(gson.toJson(parser.parseToMap(args)));
            client.work();
        }
      /* Client client = new Client("{\"type\":\"exit\"}");
        client.work();*/
    }
}
