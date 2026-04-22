package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.controller.history.CommandHistory;
import uz.itpu.danial_sarsenov.controller.storage.LastResultStorage;
import uz.itpu.danial_sarsenov.security.Session;
import uz.itpu.danial_sarsenov.security.SessionContext;

public class ControllerImpl implements Controller {

    private static final String DELIMITER = " +";

    @Override
    public Response execute(Request request) {

        if (request == null || request.requestString().isBlank()) {
            return new ResponseImpl("Empty command", false, false);
        }

        String[] parts = request.requestString()
                .trim()
                .split(DELIMITER);

        Command command = CommandProvider.getCommand(parts[0]);

        // =========================
        // AUTH CHECK (clean version)
        // =========================
        Session session = SessionContext.get();

        if (command instanceof SecuredCommand securedCommand) {

            if (session == null) {
                return new ResponseImpl(
                        "Not authenticated. Use: login <username> <password>",
                        false,
                        false
                );
            }

            if (session.getRole() != securedCommand.requiredRole()) {
                return new ResponseImpl("Access denied", false, false);
            }
        }

        // =========================
        // EXECUTE
        // =========================
        Response response = command.execute(parts);

        // =========================
        // HISTORY
        // =========================
        CommandHistory.add(request.requestString());

        // =========================
        // LAST RESULT STORAGE
        // =========================
        if (response != null && response.isOk() && response.getMessage() != null) {
            LastResultStorage.save(response.getMessage());
        }

        return response;
    }
}