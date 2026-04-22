package uz.itpu.danial_sarsenov;

import uz.itpu.danial_sarsenov.auth.AuthService;
import uz.itpu.danial_sarsenov.config.Config;
import uz.itpu.danial_sarsenov.config.PropertiesConfigImpl;
import uz.itpu.danial_sarsenov.server.AuthContext;
import uz.itpu.danial_sarsenov.server.Server;
import uz.itpu.danial_sarsenov.view.*;


public class Application {
    int port = Integer.parseInt(System.getProperty("app.port", "12345"));
    public static void main(String[] args) {
        String props = "app";
        String batchFile = null;

        if (args.length > 0) {
            props = args[0];
            if (args.length > 1) {
                batchFile = args[1];
            }
        }

        try {

            Config config = new PropertiesConfigImpl(props);
            config.init();


            AuthContext.init(new AuthService());



            int port = 12345; // можно вынести в props
            Server server = new Server(port);
            new Thread(server::start).start();

            // --- Создание view ---
            View view;
            if (batchFile != null) {
                view = new BatchViewImpl(batchFile);
            } else {
                view = new ConsoleViewImpl();
            }

            ViewFactory.init(view);

            // --- Старт view ---
            view.start();

        } catch (RuntimeException e) {
            e.printStackTrace();

            View view;
            try {
                view = ViewFactory.getInstance();
            } catch (IllegalStateException ex) {
                view = new ConsoleViewImpl();
            }
            view.crush();
        }
    }
}