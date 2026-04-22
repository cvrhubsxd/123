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
        CommandProvider.printHelp();
        System.out.println();
    }


    @Override
    public void crush() {
        System.out.println("Something went wrong. Application terminated.");
    }
}
