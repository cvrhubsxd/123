package uz.itpu.danial_sarsenov.view;

import uz.itpu.danial_sarsenov.controller.*;

import java.util.Scanner;

public class ConsoleViewImpl implements View {

    @Override
    public void start() {
        Controller controller = ControllerFactory.getInstance();


        printHelp();

        try (Scanner scanner = new Scanner(System.in)) {
            Response response = null;
            do {
                System.out.print("> ");
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                Request request = RequestFactory.of(line);
                response = controller.execute(request);
                System.out.println(response.getMessage());
            } while (!response.isExit());
        }
    }

    private void printHelp() {
        System.out.println("Welcome to the Home Appliances Warehouse App!");
        System.out.println("Available commands:");


        System.out.println("  find all                     - show all appliances");
        System.out.println("  find kitchen                  - show only kitchen appliances");
        System.out.println("  find electric                 - show only electric appliances");
        System.out.println("  find cooling                  - show only cooling appliances");
        System.out.println("  find <name|category|price>   - smart search with partial match and priority");


        System.out.println("  history                       - show last 10 commands");
        System.out.println("  repeat <n>                    - repeat nth command from history");


        System.out.println("  export last_result <file>     - save last search result to file (.txt or .csv)");
        System.out.println("  export all <file>             - save all products to file (.txt or .csv)");


        System.out.println("  stats                         - warehouse statistics (total count, total cost, most expensive, category with max items)");


        System.out.println("  demo                          - run tutorial/demo with example commands");


        System.out.println("  exit                          - exit the application");
        System.out.println();
    }


    @Override
    public void crush() {
        System.out.println("Something went wrong. Application terminated.");
    }
}
