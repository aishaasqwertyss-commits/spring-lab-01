package kz.iitu.springlab.security;

public class SecurityContext {
    private static String currentRole = "GUEST";

    public static String getCurrentRole() {
        return currentRole;
    }

    public static void setCurrentRole(String role) {
        currentRole = role;
    }
}