package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.controller.storage.LastResultStorage;

import java.io.FileWriter;
import java.io.IOException;

public class ExportCommand implements Command {

    @Override
    public Response execute(String[] args) {
        if (args.length != 3 || !"last".equalsIgnoreCase(args[1])) {
            return new ResponseImpl(
                    "Usage: export last <filename>",
                    false,
                    false
            );
        }

        String data = LastResultStorage.get();
        if (data == null || data.isBlank()) {
            return new ResponseImpl("Nothing to export", false, false);
        }

        String fileName = args[2];

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(data);
        } catch (IOException e) {
            return new ResponseImpl(
                    "Failed to write file: " + e.getMessage(),
                    false,
                    false
            );
        }

        return new ResponseImpl(
                "Exported successfully to " + fileName,
                true,
                false
        );
    }
}
