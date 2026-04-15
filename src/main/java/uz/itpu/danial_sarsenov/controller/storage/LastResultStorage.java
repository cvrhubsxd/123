package uz.itpu.danial_sarsenov.controller.storage;

public final class LastResultStorage {

    private static String lastResult;

    private LastResultStorage() {}

    public static void save(String result) {
        lastResult = result;
    }

    public static String get() {
        return lastResult;
    }
}
