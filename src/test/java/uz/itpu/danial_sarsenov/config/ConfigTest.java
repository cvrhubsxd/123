package uz.itpu.danial_sarsenov.config;

import org.junit.jupiter.api.Test;
import uz.itpu.danial_sarsenov.view.ConsoleViewImpl;
import uz.itpu.danial_sarsenov.view.ViewFactory;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesConfigImplTest {

    @Test
    void init_shouldNotThrowException() {
        PropertiesConfigImpl config = new PropertiesConfigImpl("app.properties");

        assertDoesNotThrow(config::init);
    }

    @Test
    void init_shouldSetConsoleViewInViewFactory() {
        PropertiesConfigImpl config = new PropertiesConfigImpl("app.properties");

        config.init();

        assertTrue(ViewFactory.getInstance() instanceof ConsoleViewImpl);
    }
}
