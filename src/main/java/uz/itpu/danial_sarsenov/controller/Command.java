package uz.itpu.danial_sarsenov.controller;

public interface Command {
    Response execute(String[] args);
}
