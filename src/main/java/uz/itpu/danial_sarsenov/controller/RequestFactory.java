package uz.itpu.danial_sarsenov.controller;

public final class RequestFactory {

    private RequestFactory() {}

    public static Request of(String commandLine) {
        return new RequestImpl(commandLine);
    }
}
