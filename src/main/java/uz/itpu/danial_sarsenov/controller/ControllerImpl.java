package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.controller.history.CommandHistory;
import uz.itpu.danial_sarsenov.controller.storage.LastResultStorage;

public class ControllerImpl implements Controller {

    private static final String DELIMITER = " +";

    @Override
    public Response execute(Request request) {
        if (request == null || request.requestString().isBlank()) {
            return new ResponseImpl("Empty command", false, false);
        }

        String[] parts = request.requestString().trim().split(DELIMITER);
        Command command = CommandProvider.getCommand(parts[0]);

        Response response = command.execute(parts);

        CommandHistory.add(request.requestString());

        if (response.getMessage() != null && response.isOk()) {
            LastResultStorage.save(response.getMessage());
        }

        return response;
    }
}
