package com.transportpark.model.service.impl;

import com.transportpark.model.domain.Assignment;
import com.transportpark.model.domain.Bus;
import com.transportpark.model.domain.Route;
import com.transportpark.model.domain.User;
import com.transportpark.model.entity.AssignmentEntity;
import com.transportpark.model.entity.BusEntity;
import com.transportpark.model.entity.RouteEntity;
import com.transportpark.model.exception.AssignmentsNotExistRuntimeException;
import com.transportpark.model.exception.EntityNotFoundRuntimeException;
import com.transportpark.model.exception.InvalidDataRuntimeException;
import com.transportpark.model.exception.RouteNotExistRuntimeException;
import com.transportpark.model.repositories.AssignmentRepository;
import com.transportpark.model.repositories.RouteRepository;
import com.transportpark.model.service.BusService;
import com.transportpark.model.service.RouteService;
import com.transportpark.model.service.mapper.AssignmentMapper;
import com.transportpark.model.service.mapper.BusMapper;
import com.transportpark.model.service.mapper.RouteMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RouteServiceImpl implements RouteService {

    private static final int CANCELED = 1;
    private static final int NOT_CANCELED = 0;
    private static final String WORK = "work";
    private static final String FREE = "free";
    private final BusService busService;
    private final RouteRepository routeRepository;
    private final AssignmentRepository assignmentRepository;
    private final RouteMapper routeMapper;
    private final BusMapper busMapper;
    private final AssignmentMapper assignmentMapper;

    @Override
    public Assignment addAssignment(Assignment assignment) {
        if (Objects.isNull(assignment)) {
            log.warn("Incorrect assignment data");
            throw new InvalidDataRuntimeException("Incorrect assignment data");
        }
        Bus existsBus = busService.findByCode(assignment.getBus().getCode());
        assignment.setBus(existsBus);
        assignment.setCanceled(NOT_CANCELED);
        return assignment;
    }

    @Override
    public void addRoute(List<Assignment> assignments) {
        Route route = new Route();
        route.setStatus(NOT_CANCELED);
        route = routeMapper.routeEntityToRoute(routeRepository.save(routeMapper.routeToRouteEntity(route)));

        for (Assignment assignment : assignments) {
            assignment.setRoute(route);
            AssignmentEntity assignmentEntity = assignmentMapper.assignmentToAssignmentEntity(assignment);
            assignmentRepository.save(assignmentEntity);
        }
    }

    @Override
    public Route findById(Long routeId) {
        return routeMapper.routeEntityToRoute(routeRepository.findById(routeId)
                .orElse(null));
    }

    @Override
    public Assignment findAssignmentsByRoute(Long routeId) {
        Route route = routeMapper.routeEntityToRoute(routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find route by this id")));

        RouteEntity routeEntity = routeMapper.routeToRouteEntity(route);
        AssignmentEntity assignmentEntity = assignmentRepository.findByRoute(routeEntity).orElse(null);
        return assignmentMapper.assignmentEntityToAssignment(assignmentEntity);
    }

    @Override
    public void cancelRouteAssignments(Assignment assignment, Integer count) {
        if (Objects.isNull(assignment) || Objects.isNull(count) || count < 0) {
            log.warn("Assignments not exist");
            throw new AssignmentsNotExistRuntimeException("Assignments not exist");
        }
        assignment.setCanceled(CANCELED);
        AssignmentEntity assignmentEntity = assignmentMapper.assignmentToAssignmentEntity(assignment);
        assignmentRepository.save(assignmentEntity);
        Route route = assignment.getRoute();
        RouteEntity routeEntity = routeMapper.routeToRouteEntity(route);
        routeRepository.save(routeEntity);
        Bus bus = assignment.getBus();
        bus.setStatus(FREE);
        bus.setDriver(null);
        busService.updateBus(bus);
    }

    @Override
    public void appointRouteAssignments(User driver) {
        if (Objects.isNull(driver)) {
            log.warn("Driver not exist");
            throw new RouteNotExistRuntimeException("Driver not exist");
        }
        Bus bus = busService.findByDriver(driver);
        bus.setStatus(WORK);
        busService.updateBus(bus);
    }

    @Override
    public Assignment findByDriver(User driver) {
        if (Objects.isNull(driver)) {
            log.warn("User not exist");
            throw new EntityNotFoundRuntimeException("User not exist");
        }
        Bus bus = busService.findByDriver(driver);
        BusEntity busEntity = busMapper.busToBusEntity(bus);
        AssignmentEntity assignmentEntity = assignmentRepository.findByBus(busEntity).orElse(null);
        return assignmentMapper.assignmentEntityToAssignment(assignmentEntity);
    }
}
