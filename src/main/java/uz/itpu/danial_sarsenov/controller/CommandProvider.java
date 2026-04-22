package uz.itpu.danial_sarsenov.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public final class CommandProvider {

    private static final Map<String, Command> COMMANDS = new HashMap<>();
    private static final Map<String, String> DESCRIPTIONS = new HashMap<>();

    static {
        // ===== USER COMMANDS =====
        register("find", new FindCommand(),
                "find <filter> [params] - search products");

        register("history", new HistoryCommand(),
                "history - show last 10 commands");

        register("repeat", new RepeatCommand(),
                "repeat <n> - repeat nth command");

        register("export", new ExportCommand(),
                "export last <file> - save last result");

        register("stats", new StatsCommand(),
                "stats - warehouse statistics");

        register("demo", new DemoCommand(),
                "demo - run demo scenario");

        register("logout", new LogoutCommand(),
                "logout - logout from system");

        register("exit", new ExitCommand(),
                "exit - close application");

        // ===== ADMIN =====
        register("login", new LoginCommand(),
                "login <user> <pass> - authenticate");

        register("add_user", new AddUserCommand(),
                "add_user <u> <p> <role> - create user");

        register("delete_user", new DeleteUserCommand(),
                "delete_user <u> - remove user");

        register("list_users", new ListUsersCommand(),
                "list_users - show all users");

        // ===== PRODUCTS =====
        register("add_product", new AddProductCommand(),
                "add_product <id> <name> <cat> <price> <qty>");

        register("remove_product", new RemoveProductCommand(),
                "remove_product <id> - delete product");
    }

    private CommandProvider() {}

    private static void register(String name, Command cmd, String description) {
        COMMANDS.put(name, cmd);
        DESCRIPTIONS.put(name, description);
    }

    public static Command getCommand(String name) {
        if (name == null) return new WrongCommand();

        Command command = COMMANDS.get(name.toLowerCase());
        return command != null ? command : new WrongCommand();
    }

    // =========================
    // PRINT HELP (единый источник)
    // =========================
    public static void printHelp() {
        System.out.println("Available commands:");

        // сортировка чтобы выглядело как у нормальных людей
        Map<String, String> sorted = new TreeMap<>(DESCRIPTIONS);

        for (String cmd : sorted.keySet()) {
            System.out.printf("  %-15s - %s%n", cmd, sorted.get(cmd));
        }
    }
}