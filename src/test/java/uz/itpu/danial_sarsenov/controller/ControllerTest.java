package uz.itpu.danial_sarsenov.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private Controller controller;

    @BeforeEach
    void setUp() {
        ControllerFactory.init();
        controller = ControllerFactory.getInstance();
    }

    @Test
    void execute_shouldReturnEmptyCommandMessage_whenRequestIsEmpty() {
        Response response = controller.execute(RequestFactory.of(""));
        assertFalse(response.isOk());
        assertEquals("Empty command", response.getMessage());
    }

    @Test
    void execute_shouldReturnWrongCommand_whenUnknownCommand() {
        Response response = controller.execute(RequestFactory.of("foobar"));
        assertFalse(response.isOk());
        assertEquals("Wrong command", response.getMessage());
    }

    @Test
    void execute_shouldReturnExitMessage_whenExitCommand() {
        Response response = controller.execute(RequestFactory.of("exit"));
        assertTrue(response.isOk());
        assertTrue(response.isExit());
        assertEquals("Bye 👋", response.getMessage());
    }

    @Test
    void execute_shouldReturnFindUsage_whenFindCommandWithoutArgs() {
        Response response = controller.execute(RequestFactory.of("find"));
        assertFalse(response.isOk());
        assertTrue(response.getMessage().contains("Usage"));
    }

    @Test
    void execute_shouldReturnUnknownFilter_whenFindCommandWithInvalidFilter() {
        Response response = controller.execute(RequestFactory.of("find unknown"));
        assertFalse(response.isOk());
        assertTrue(response.getMessage().contains("Unknown filter"));
    }
}
