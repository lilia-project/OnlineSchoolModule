package org.lilia.logger.exception;

public class NoSuchCourseIdException extends RuntimeException {
    public NoSuchCourseIdException(int courseId) {
        super("no such courseId exist " + courseId);
    }
}
