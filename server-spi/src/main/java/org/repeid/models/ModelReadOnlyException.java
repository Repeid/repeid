package org.repeid.models;

public class ModelReadOnlyException extends ModelException {

    public ModelReadOnlyException() {
    }

    public ModelReadOnlyException(String message) {
        super(message);
    }

    public ModelReadOnlyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelReadOnlyException(Throwable cause) {
        super(cause);
    }
}
