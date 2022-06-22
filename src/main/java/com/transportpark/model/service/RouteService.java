package com.transportpark.model.service;

import com.transportpark.model.domain.Assignment;
import com.transportpark.model.domain.Route;
import com.transportpark.model.domain.User;

import java.util.List;

public interface RouteService {

    Assignment addAssignment(Assignment assignment);

    void addRoute(List<Assignment> assignments);

    Route findById(Long routeId);

    Assignment findAssignmentsByRoute(Long routeId);

    void cancelRouteAssignments(Assignment assignment, Integer count);

    void appointRouteAssignments(User driver);

    Assignment findByDriver(User driver);
}
