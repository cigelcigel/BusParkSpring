package com.transportpark.model.exception;

public class RouteNotExistRuntimeException extends RuntimeException {

    public RouteNotExistRuntimeException() {
    }

    public RouteNotExistRuntimeException(String message) {
        super(message);
    }

    public RouteNotExistRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
