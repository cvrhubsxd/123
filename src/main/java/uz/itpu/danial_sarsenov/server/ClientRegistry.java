package uz.itpu.danial_sarsenov.server;

import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientRegistry {

    private static final List<PrintWriter> clients = new CopyOnWriteArrayList<>();

    public static void register(PrintWriter out) {
        clients.add(out);
    }

    public static void unregister(PrintWriter out) {
        clients.remove(out);
    }

    public static void broadcast(String message) {
        for (PrintWriter c : clients) {
            c.println("[BROADCAST] " + message);
        }
    }
}