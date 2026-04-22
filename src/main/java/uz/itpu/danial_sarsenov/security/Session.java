package uz.itpu.danial_sarsenov.security;

import uz.itpu.danial_sarsenov.auth.UserRole;

public class Session {

    private final String username;
    private final UserRole role;

    public Session(String username, UserRole role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }

    public boolean isUser() {
        return role == UserRole.USER;
    }
}