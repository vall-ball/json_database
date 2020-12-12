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
                    String[] arr = msg.split(" ");
                    System.out.println("Received: " + msg);
                    String answer = "A record #" + arr[arr.length - 1] + " was sent!";
                    output.writeUTF(answer); // resend it to the client
                    System.out.println("Sent: " + answer);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}