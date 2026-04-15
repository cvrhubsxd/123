package uz.itpu.danial_sarsenov.controller;

public class ExitCommand implements Command {

    @Override
    public Response execute(String[] args) {
        return new ResponseImpl("Bye 👋", true, true);
    }
}
