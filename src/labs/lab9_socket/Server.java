package labs.lab9_socket;

import java.io.*;
        import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 5005;

    public static void main(String[] args) {
        System.out.println("[Server] Starting on port " + PORT + " …");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[Server] Waiting for a client to connect…");
            try (Socket socket = serverSocket.accept();
                 DataInputStream in = new DataInputStream(socket.getInputStream());
                 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                 BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

                System.out.println("[Server] Client connected from " + socket.getInetAddress() + ":" + socket.getPort());
                System.out.println("[Server] Type messages and press Enter. Type /quit to exit.");

                // Thread to read from client and print to console
                Thread reader = new Thread(() -> {
                    try {
                        while (true) {
                            String msg = in.readUTF(); // blocks
                            System.out.println("[Client] " + msg);
                        }
                    } catch (EOFException eof) {
                        System.out.println("[Server] Client disconnected.");
                    } catch (IOException e) {
                        System.out.println("[Server] Read error: " + e.getMessage());
                    }
                });
                reader.setDaemon(true);
                reader.start();

                // Main thread: read from console and send to client
                String line;
                while ((line = console.readLine()) != null) {
                    if (line.equalsIgnoreCase("/quit")) {
                        System.out.println("[Server] Closing connection…");
                        break;
                    }
                    out.writeUTF(line);
                    out.flush();
                }

            }
        } catch (IOException e) {
            System.err.println("[Server] Error: " + e.getMessage());
        }
        System.out.println("[Server] Stopped.");
    }
}
