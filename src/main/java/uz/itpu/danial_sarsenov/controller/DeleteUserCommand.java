package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.auth.AuthService;
import uz.itpu.danial_sarsenov.auth.UserRole;
import uz.itpu.danial_sarsenov.server.AuthContext;

public class DeleteUserCommand implements SecuredCommand {

    @Override
    public UserRole requiredRole() {
        return UserRole.ADMIN;
    }

    @Override
    public Response execute(String[] args) {

        if (args.length != 1) {
            return new ResponseImpl("Usage: delete_user username", false, false);
        }

        AuthService auth = AuthContext.getAuthService();

        boolean ok = auth.deleteUser(args[0]);

        return new ResponseImpl(ok ? "User deleted" : "Not found", ok, false);
    }
}