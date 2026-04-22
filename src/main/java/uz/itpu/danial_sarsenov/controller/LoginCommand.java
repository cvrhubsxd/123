package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.auth.AuthService;
import uz.itpu.danial_sarsenov.auth.UserRole;
import uz.itpu.danial_sarsenov.security.Session;
import uz.itpu.danial_sarsenov.security.SessionContext;
import uz.itpu.danial_sarsenov.server.AuthContext;

public class LoginCommand implements Command {

    @Override
    public Response execute(String[] args) {

        // args[0] = login
        // args[1] = username
        // args[2] = password

        if (args.length != 3) {
            return new ResponseImpl("Usage: login username password", false, false);
        }

        String username = args[1];
        String password = args[2];

        AuthService auth = AuthContext.getAuthService();

        UserRole role = auth.login(username, password);

        if (role == null) {
            return new ResponseImpl("Invalid credentials", false, false);
        }

        SessionContext.set(new Session(username, role));

        return new ResponseImpl("Logged in as " + role, true, false);
    }
}