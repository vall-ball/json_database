package server;

import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) throws UnknownHostException {
        Server server = new Server();
        server.work();
    }
}
