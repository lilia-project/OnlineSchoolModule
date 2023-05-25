package org.lilia.logger.exception;

public class NoSuchRoleException extends RuntimeException {
    public NoSuchRoleException() {
        super("no such Role exist ");
    }
}
