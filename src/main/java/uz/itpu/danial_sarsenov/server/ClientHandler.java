package uz.itpu.danial_sarsenov.server;

import uz.itpu.danial_sarsenov.auth.AuthService;
import uz.itpu.danial_sarsenov.auth.UserRole;
import uz.itpu.danial_sarsenov.controller.*;
import uz.itpu.danial_sarsenov.controller.storage.ProductStorage;
import uz.itpu.danial_sarsenov.entity.Product;

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
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("Welcome! Please login: username password");

            while (role == null) {
                String line = in.readLine();
                if (line == null) break;
                String[] parts = line.split(" ");
                if (parts.length != 2) {
                    out.println("Invalid format. Use: username password");
                    continue;
                }
                username = parts[0];
                String password = parts[1];
                role = authService.login(username, password);
                if (role == null) {
                    out.println("Login failed. Try again.");
                } else {
                    out.println("Login successful as " + role);
                }
            }

            Controller controller = ControllerFactory.getInstance();
            out.println("Enter commands. Type 'help' for list.");

            String commandLine;
            while ((commandLine = in.readLine()) != null) {
                if (commandLine.equalsIgnoreCase("exit")) break;
                if (commandLine.equalsIgnoreCase("help")) {
                    out.println("Available commands: find, add, remove, stats, list_users, add_user, delete_user, exit");
                    continue;
                }

                // Admin-only commands
                if (role == UserRole.ADMIN) {
                    if (commandLine.startsWith("list_users")) {
                        authService.listUsers().forEach((u, r) -> out.println(u + " : " + r));
                        continue;
                    } else if (commandLine.startsWith("add_user")) {
                        String[] args = commandLine.split(" ");
                        if (args.length != 4) {
                            out.println("Usage: add_user username password role");
                            continue;
                        }
                        boolean ok = authService.addUser(args[1], args[2], UserRole.valueOf(args[3].toUpperCase()));
                        out.println(ok ? "User added" : "Failed to add user");
                        continue;
                    } else if (commandLine.startsWith("delete_user")) {
                        String[] args = commandLine.split(" ");
                        if (args.length != 2) {
                            out.println("Usage: delete_user username");
                            continue;
                        }
                        boolean ok = authService.deleteUser(args[1]);
                        out.println(ok ? "User deleted" : "Failed to delete user");
                        continue;
                    }
                }

                // User commands
                if (role == UserRole.USER || role == UserRole.ADMIN) {
                    if (commandLine.startsWith("add_product")) {
                        try {
                            String[] parts = commandLine.split(",");
                            if (parts.length != 6) {
                                out.println("Usage: add_product id,name,category,price,quantity,type");
                                continue;
                            }
                            Product p = new Product(
                                    Integer.parseInt(parts[0].split("=")[1].trim()),
                                    parts[1].split("=")[1].trim(),
                                    parts[2].split("=")[1].trim(),
                                    Double.parseDouble(parts[3].split("=")[1].trim()),
                                    Integer.parseInt(parts[4].split("=")[1].trim()),
                                    "", parts[5].split("=")[1].trim(), 0,0
                            );
                            ProductStorage.add(p);
                            out.println("Product added: " + p.getName());
                        } catch (Exception e) {
                            out.println("Failed to add product: " + e.getMessage());
                        }
                        continue;
                    } else if (commandLine.startsWith("remove_product")) {
                        try {
                            int id = Integer.parseInt(commandLine.split(" ")[1]);
                            boolean removed = ProductStorage.getAll().removeIf(p -> p.getId() == id);
                            out.println(removed ? "Product removed" : "Product not found");
                        } catch (Exception e) {
                            out.println("Usage: remove_product <id>");
                        }
                        continue;
                    }
                }

                // Default to old command execution
                Request req = RequestFactory.of(commandLine);
                Response resp = controller.execute(req);
                out.println(resp.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}