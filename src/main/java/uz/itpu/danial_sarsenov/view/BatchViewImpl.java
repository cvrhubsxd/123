package uz.itpu.danial_sarsenov.view;

import uz.itpu.danial_sarsenov.controller.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BatchViewImpl implements View {

    private final String fileName;

    public BatchViewImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void start() {
        System.out.println("Запуск batch-режима...");
        System.out.println("Доступные команды:");
        System.out.println("  find <product|supplier|warehouse> [key=value ...] - поиск сущностей");
        System.out.println("  stats - вывод статистики");
        System.out.println("  export - экспорт в CSV");
        System.out.println("  sort category=<cat> price=<low>;<high> order=<asc|desc> - сортировка");
        System.out.println("  exit - завершение batch");
        System.out.println();

        Controller controller = ControllerFactory.getInstance();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                Request request = RequestFactory.of(line);
                Response response = controller.execute(request);
                System.out.println(response.getMessage());
                if (response.isExit()) break;
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения batch-файла: " + fileName, e);
        }
    }

    @Override
    public void crush() {
        System.out.println("Batch execution failed");
    }
}
