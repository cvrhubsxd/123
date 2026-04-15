package uz.itpu.danial_sarsenov.server;

import uz.itpu.danial_sarsenov.auth.AuthService;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port;
    private final AuthService authService;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public Server(int port) {
        this.port = port;
        this.authService = new AuthService();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started at port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(new ClientHandler(clientSocket, authService));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}