package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.controller.history.CommandHistory;

import java.util.List;

public class HistoryCommand implements Command {

    @Override
    public Response execute(String[] args) {
        List<String> history = CommandHistory.getAll();

        if (history.isEmpty()) {
            return new ResponseImpl("History is empty", true, false);
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < history.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(history.get(i))
                    .append(System.lineSeparator());
        }

        return new ResponseImpl(sb.toString().trim(), true, false);
    }
}
