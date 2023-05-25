package org.lilia.logger.exception;

public class NoSuchMaterialIdException extends RuntimeException {
    public NoSuchMaterialIdException(int additionMaterialId) {
        super("no such materialId exist " + additionMaterialId);
    }
}
