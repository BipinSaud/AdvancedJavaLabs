package labs.lab9_socket;

import java.io.*;
import java.net.Socket;

public class Client {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 5005;

    public static void main(String[] args) {
        System.out.println("[Client] Connecting to " + HOST + ":" + PORT + " …");
        try (Socket socket = new Socket(HOST, PORT);
             DataInputStream in = new DataInputStream(socket.getInputStream());
             DataOutputStream out = new DataOutputStream(socket.getOutputStream());
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("[Client] Connected. Type messages and press Enter. Type /quit to exit.");

            // Thread to read from server and print to console
            Thread reader = new Thread(() -> {
                try {
                    while (true) {
                        String msg = in.readUTF(); // blocks
                        System.out.println("[Server] " + msg);
                    }
                } catch (EOFException eof) {
                    System.out.println("[Client] Server closed the connection.");
                } catch (IOException e) {
                    System.out.println("[Client] Read error: " + e.getMessage());
                }
            });
            reader.setDaemon(true);
            reader.start();

            // Main thread: read from console and send to server
            String line;
            while ((line = console.readLine()) != null) {
                if (line.equalsIgnoreCase("/quit")) {
                    System.out.println("[Client] Closing connection…");
                    break;
                }
                out.writeUTF(line);
                out.flush();
            }

        } catch (IOException e) {
            System.err.println("[Client] Error: " + e.getMessage());
        }
        System.out.println("[Client] Stopped.");
    }
}
