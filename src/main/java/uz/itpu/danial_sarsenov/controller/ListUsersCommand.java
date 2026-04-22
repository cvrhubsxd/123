package uz.itpu.danial_sarsenov.controller;

import uz.itpu.danial_sarsenov.auth.AuthService;
import uz.itpu.danial_sarsenov.auth.UserRole;
import uz.itpu.danial_sarsenov.server.AuthContext;

public class ListUsersCommand implements SecuredCommand {

    @Override
    public UserRole requiredRole() {
        return UserRole.ADMIN;
    }

    @Override
    public Response execute(String[] args) {

        AuthService auth = AuthContext.getAuthService();

        StringBuilder sb = new StringBuilder();

        auth.listUsers().forEach((u, r) ->
                sb.append(u).append(" -> ").append(r).append("\n")
        );

        return new ResponseImpl(sb.toString(), true, false);
    }
}