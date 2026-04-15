package uz.itpu.danial_sarsenov.controller.history;

import java.util.ArrayList;
import java.util.List;

public final class CommandHistory {

    private static final int LIMIT = 10;
    private static final List<String> HISTORY = new ArrayList<>();

    private CommandHistory() {}

    public static void add(String command) {
        if (command == null || command.isBlank()) return;

        if (HISTORY.size() == LIMIT) {
            HISTORY.remove(0);
        }
        HISTORY.add(command);
    }

    public static List<String> getAll() {
        return List.copyOf(HISTORY);
    }

    public static String get(int index) {
        if (index < 1 || index > HISTORY.size()) return null;
        return HISTORY.get(index - 1);
    }
}
