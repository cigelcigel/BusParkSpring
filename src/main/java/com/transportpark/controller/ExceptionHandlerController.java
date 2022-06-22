package com.transportpark.controller;

import com.transportpark.model.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

public class ExceptionHandlerController {
    @ExceptionHandler({SQLException.class,
            NullPointerException.class,
            AssignmentsNotExistRuntimeException.class,
            EntityNotFoundRuntimeException.class,
            InvalidDataRuntimeException.class,
            InvalidIdRuntimeException.class,
            RouteNotExistRuntimeException.class,
            IllegalArgumentException.class,
            Throwable.class})
    public String handleException() {
        return "errorSpring";
    }

}
