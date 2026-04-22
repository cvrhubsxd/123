package uz.itpu.danial_sarsenov.server;

import uz.itpu.danial_sarsenov.auth.AuthService;

public class AuthContext {
    private static AuthService authService;

    public static void init(AuthService service) {
        authService = service;
    }

    public static AuthService getAuthService() {
        return authService;
    }
}