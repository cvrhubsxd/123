package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.controller.history.CommandHistory;

public class RepeatCommand implements Command {

    @Override
    public Response execute(String[] args) {

        if (args.length < 2) {
            return new ResponseImpl("Usage: repeat <number>", false, false);
        }

        int index;
        try {
            index = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            return new ResponseImpl("Invalid number", false, false);
        }

        String command = CommandHistory.get(index);

        if (command == null) {
            return new ResponseImpl("No command with such index", false, false);
        }

        return ControllerFactory
                .getInstance()
                .execute(RequestFactory.of(command));
    }
}