package client;

import com.google.gson.Gson;

import java.net.UnknownHostException;


public class Main {

    public static void main(String[] args) throws UnknownHostException {
        ArgsParser parser = new ArgsParser();
        Gson gson = new Gson();
        Client client = new Client(gson.toJson(parser.parseToMap(args)));
        client.work();
    }
}
