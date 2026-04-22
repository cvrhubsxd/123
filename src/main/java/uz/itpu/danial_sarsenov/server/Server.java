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
    private final ExecutorService executor;

    public Server(int port) {
        this.port = port;
        this.authService = AuthContext.getAuthService();
        this.executor = Executors.newCachedThreadPool();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("[SERVER] Started on port " + port);

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();

                    System.out.println("[SERVER] Client connected: " +
                            clientSocket.getInetAddress());

                    executor.submit(
                            new ClientHandler(clientSocket, authService)
                    );

                } catch (IOException e) {
                    System.out.println("[SERVER] Failed to accept client: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Server failed to start", e);
        } finally {
            executor.shutdown();
        }
    }
}