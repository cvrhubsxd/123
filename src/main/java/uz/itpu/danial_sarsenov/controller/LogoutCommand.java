package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.security.SessionContext;

public class LogoutCommand implements Command {

    @Override
    public Response execute(String[] args) {

        SessionContext.clear();

        return new ResponseImpl("Logged out", true, false);
    }
}