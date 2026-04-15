package uz.itpu.danial_sarsenov.auth;

import java.util.HashMap;
import java.util.Map;

public class AuthService {
    private final Map<String, String> users = new HashMap<>(); // username -> password
    private final Map<String, UserRole> roles = new HashMap<>();

    public AuthService() {
        // default users
        users.put("admin", "admin123");
        roles.put("admin", UserRole.ADMIN);

        users.put("user1", "user123");
        roles.put("user1", UserRole.USER);
    }

    public UserRole login(String username, String password) {
        if (!users.containsKey(username)) return null;
        if (!users.get(username).equals(password)) return null;
        return roles.get(username);
    }

    public boolean addUser(String username, String password, UserRole role) {
        if (users.containsKey(username)) return false;
        users.put(username, password);
        roles.put(username, role);
        return true;
    }

    public boolean deleteUser(String username) {
        if (!users.containsKey(username)) return false;
        users.remove(username);
        roles.remove(username);
        return true;
    }

    public Map<String, UserRole> listUsers() {
        return Map.copyOf(roles);
    }
}