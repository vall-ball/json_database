package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    String address = "127.0.0.1";
    int port = 23456;
    String command;

    Client(String command) {
        this.command = command;
    }

    public void work() throws UnknownHostException {
        System.out.println("Client started!");
        try (
                Socket socket = new Socket(InetAddress.getByName(address), port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        ) {
            output.writeUTF(command); // sending message to the server
            System.out.println("Sent: " + command);
            String receivedMsg = input.readUTF(); // response message
            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
