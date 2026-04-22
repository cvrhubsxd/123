package uz.itpu.danial_sarsenov.server;

import uz.itpu.danial_sarsenov.auth.AuthService;
import uz.itpu.danial_sarsenov.auth.UserRole;
import uz.itpu.danial_sarsenov.controller.*;
import uz.itpu.danial_sarsenov.security.Session;
import uz.itpu.danial_sarsenov.security.SessionContext;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;
    private final AuthService authService;

    private UserRole role;
    private String username;

    public ClientHandler(Socket socket, AuthService authService) {
        this.socket = socket;
        this.authService = authService;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {

            out.println("Login: username password");

            while (role == null) {

                String line = in.readLine();
                if (line == null) return;

                String[] parts = line.trim().split(" ");

                if (parts.length != 2) {
                    out.println("Format: username password");
                    continue;
                }

                username = parts[0];
                role = authService.login(parts[0], parts[1]);

                if (role == null) {
                    out.println("Login failed");
                    continue;
                }

                SessionContext.set(new Session(username, role));
                out.println("Welcome " + username + " (" + role + ")");
            }

            Controller controller = ControllerFactory.getInstance();
            out.println("Ready for commands");

            String line;
            while ((line = in.readLine()) != null) {

                line = line.trim();

                if (line.equalsIgnoreCase("exit")) {
                    out.println("Bye");
                    break;
                }

                try {
                    SessionContext.set(new Session(username, role));

                    Request request = RequestFactory.of(line);
                    Response response = controller.execute(request);

                    out.println(response.getMessage());

                } finally {
                    SessionContext.clear();
                }
            }

        } catch (IOException e) {
            System.out.println("[ClientHandler] Error: " + e.getMessage());
        } finally {

            SessionContext.clear();

            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }
}