package source.ConsoleMessageHandlers;

import org.springframework.stereotype.Component;

@Component
public class ConsoleMessagesHandler implements ConsoleHandleable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    @Override
    public void handleSuccessMessage(String message) {
        printColoredMessage(message, ANSI_GREEN);
    }

    @Override
    public void handleFailureMessage(String message) {
        printColoredMessage(message, ANSI_RED);
    }

    private void printColoredMessage(String message, String color) {
        System.out.println(color + message + ANSI_RESET);
    }
}
