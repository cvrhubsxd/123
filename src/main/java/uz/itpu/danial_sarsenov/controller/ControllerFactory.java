package uz.itpu.danial_sarsenov.controller;

public final class ControllerFactory {

    private static Controller controller;

    private ControllerFactory() {}

    public static void init() {
        controller = new ControllerImpl();
    }

    public static Controller getInstance() {
        if (controller == null) {
            throw new IllegalStateException("Controller not initialized");
        }
        return controller;
    }
}
