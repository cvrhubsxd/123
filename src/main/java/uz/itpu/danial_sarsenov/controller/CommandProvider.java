package uz.itpu.danial_sarsenov.controller;

import java.util.Map;

public final class CommandProvider {

    private static final Map<String, Command> COMMANDS = Map.of(
            "find", new FindCommand(),
            "exit", new ExitCommand(),
            "history", new HistoryCommand(),
            "repeat", new RepeatCommand(),
            "export", new ExportCommand(),
            "stats", new StatsCommand(),
            "demo", new DemoCommand()

    );

    private CommandProvider() {}

    public static Command getCommand(String name) {
        if (name == null || name.isBlank()) {
            return new WrongCommand();
        }
        return COMMANDS.getOrDefault(name.toLowerCase(), new WrongCommand());
    }
}
