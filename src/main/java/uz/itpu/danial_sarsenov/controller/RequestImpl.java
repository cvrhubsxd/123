package uz.itpu.danial_sarsenov.controller;

public record RequestImpl(String request) implements Request {
    @Override
    public String requestString() {
        return request;
    }
}
