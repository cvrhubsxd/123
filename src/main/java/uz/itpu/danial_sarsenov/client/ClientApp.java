package uz.itpu.danial_sarsenov.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            Thread readerThread = new Thread(() -> {
                try {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException ignored) {}
            });
            readerThread.start();

            while (true) {
                String cmd = scanner.nextLine();
                out.println(cmd);
                if ("exit".equalsIgnoreCase(cmd)) break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}