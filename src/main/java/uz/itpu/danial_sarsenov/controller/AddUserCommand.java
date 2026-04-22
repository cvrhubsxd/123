package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.auth.AuthService;
import uz.itpu.danial_sarsenov.auth.UserRole;
import uz.itpu.danial_sarsenov.server.AuthContext;


public class AddUserCommand implements SecuredCommand {

    @Override
    public UserRole requiredRole() {
        return UserRole.ADMIN;
    }

    @Override
    public Response execute(String[] args) {

        if (args.length != 3) {
            return new ResponseImpl("Usage: add_user username password role", false, false);
        }

        AuthService auth = AuthContext.getAuthService();

        boolean ok = auth.addUser(
                args[0],
                args[1],
                UserRole.valueOf(args[2].toUpperCase())
        );

        return new ResponseImpl(ok ? "User added" : "Failed", ok, false);
    }
}