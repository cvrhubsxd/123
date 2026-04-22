package uz.itpu.danial_sarsenov.security;

public class SessionContext {

    private static final ThreadLocal<Session> CURRENT = new ThreadLocal<>();

    public static void set(Session session) {
        if (session == null) return;
        CURRENT.set(session);
    }

    public static Session getRequired() {
        Session s = CURRENT.get();
        if (s == null) {
            throw new IllegalStateException("No active session");
        }
        return s;
    }

    public static Session get() {
        return CURRENT.get();
    }

    public static void clear() {
        CURRENT.remove();
    }
}