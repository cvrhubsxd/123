package uz.itpu.danial_sarsenov.controller;

public class DemoResponse implements Response {

    private final String message;
    private final boolean exit;

    public DemoResponse(String message, boolean exit) {
        this.message = message;
        this.exit = exit;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean isExit() {
        return exit;
    }

    @Override
    public boolean isOk() {
        return true;
    }
}
