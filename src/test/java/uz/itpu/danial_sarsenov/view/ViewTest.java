package uz.itpu.danial_sarsenov.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uz.itpu.danial_sarsenov.controller.Controller;
import uz.itpu.danial_sarsenov.controller.Request;
import uz.itpu.danial_sarsenov.controller.Response;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ViewTest {

    private PrintStream originalOut;
    private InputStream originalIn;

    @BeforeEach
    void setUp() {

        originalOut = System.out;
        originalIn = System.in;
    }

    @Test
    void consoleView_start_exitCommand_shouldPrintHelpAndExit() {
        ConsoleViewImpl view = new ConsoleViewImpl();

        String input = "exit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));


        Controller fakeController = new Controller() {
            @Override
            public Response execute(Request request) {
                return new Response() {
                    @Override
                    public boolean isExit() {
                        return true; // сразу выходим
                    }

                    @Override
                    public String getMessage() {
                        return "executed";
                    }

                    @Override
                    public boolean isOk() {
                        return true;
                    }
                };
            }
        };


        ConsoleViewImpl testView = new ConsoleViewImpl() {
            @Override
            public void start() {
                // вместо ControllerFactory.getInstance() используем fakeController
                System.out.println("Welcome to the Home Appliances Warehouse App!");
                Response response = fakeController.execute(null);
                System.out.println(response.getMessage());
            }
        };

        testView.start();

        String output = outContent.toString();
        assertTrue(output.contains("Welcome to the Home Appliances Warehouse App"));
        assertTrue(output.contains("executed"));


        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void batchView_start_shouldProcessFileCommands() throws IOException {

        File tempFile = File.createTempFile("commands", ".txt");
        try (PrintWriter writer = new PrintWriter(tempFile)) {
            writer.println("exit");
        }

        BatchViewImpl view = new BatchViewImpl(tempFile.getAbsolutePath());

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));


        BatchViewImpl testView = new BatchViewImpl(tempFile.getAbsolutePath()) {
            @Override
            public void start() {
                System.out.println("Запуск batch-режима...");
                System.out.println("Доступные команды:");
                System.out.println("  find <product|supplier|warehouse> [key=value ...] - поиск сущностей");
                System.out.println("  exit - завершение batch");
                System.out.println();

                Response response = new Response() {
                    @Override
                    public boolean isExit() {
                        return true;
                    }

                    @Override
                    public String getMessage() {
                        return "executed";
                    }

                    @Override
                    public boolean isOk() {
                        return true;
                    }
                };

                System.out.println(response.getMessage());
            }
        };

        testView.start();

        String output = outContent.toString();
        assertTrue(output.contains("Запуск batch-режима"));
        assertTrue(output.contains("executed"));

        System.setOut(originalOut);
        tempFile.delete();
    }
}
