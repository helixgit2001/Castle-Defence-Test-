package ir.ac.kntu;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClass implements Serializable {
    private Socket socket = null;
    private static final SocketClass client = new SocketClass();

    public SocketClass getClient() {
        return client;
    }

    public static void run() throws UnknownHostException, IOException, ClassNotFoundException {
        String ip = "127.0.0.1";
        int port = 1080;
        client.connectSocket(ip, port);

    }

    private void connectSocket(String ip, int port) throws UnknownHostException, IOException {
        this.socket = new Socket(ip, port);
    }

    public String print(String message) {
        try {
            PrintWriter printWriter = new PrintWriter(getSocket().getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
            printWriter.println(message);
            return bufferedReader.readLine();
        } catch (Exception e) {
            System.out.println("exception occurred");
        }
        return null;
    }

    private Socket getSocket() {
        return socket;

    }

}