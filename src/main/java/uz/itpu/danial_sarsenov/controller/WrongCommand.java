package uz.itpu.danial_sarsenov.controller;

public class WrongCommand implements Command {

    @Override
    public Response execute(String[] args) {
        return new ResponseImpl("Wrong command", false, false);
    }
}
