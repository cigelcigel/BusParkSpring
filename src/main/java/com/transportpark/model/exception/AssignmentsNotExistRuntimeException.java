package com.transportpark.model.exception;

public class AssignmentsNotExistRuntimeException extends RuntimeException {

    public AssignmentsNotExistRuntimeException() {
    }

    public AssignmentsNotExistRuntimeException(String message) {
        super(message);
    }

    public AssignmentsNotExistRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
