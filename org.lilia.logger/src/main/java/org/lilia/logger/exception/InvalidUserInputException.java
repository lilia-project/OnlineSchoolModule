package org.lilia.logger.exception;

public class InvalidUserInputException extends RuntimeException {
    public InvalidUserInputException(String data, String pattern) {
        super("expected String that matches pattern: " + pattern + " , got: " + data);
    }

    public InvalidUserInputException(String data) {
        super("expected int value, got: " + data);
    }
}
