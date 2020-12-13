package client;

import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws UnknownHostException {
        ArgsParser parser = new ArgsParser();
        Client client = new Client(parser.parse(args));
        client.work();
    }
}
