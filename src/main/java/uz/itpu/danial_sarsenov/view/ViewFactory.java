package uz.itpu.danial_sarsenov.view;

public final class ViewFactory {

    private static View instance;

    private ViewFactory() {}

    public static void init(View view) {
        instance = view;
    }

    public static View getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ViewFactory not initialized");
        }
        return instance;
    }
}
