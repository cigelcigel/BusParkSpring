package com.transportpark.model.service.impl;

import com.transportpark.model.domain.Assignment;
import com.transportpark.model.domain.Bus;
import com.transportpark.model.domain.Route;
import com.transportpark.model.entity.AssignmentEntity;
import com.transportpark.model.entity.BusEntity;
import com.transportpark.model.entity.RouteEntity;
import com.transportpark.model.exception.AssignmentsNotExistRuntimeException;
import com.transportpark.model.exception.EntityNotFoundRuntimeException;
import com.transportpark.model.exception.InvalidDataRuntimeException;
import com.transportpark.model.repositories.AssignmentRepository;
import com.transportpark.model.repositories.RouteRepository;
import com.transportpark.model.service.BusService;
import com.transportpark.model.service.RouteService;
import com.transportpark.model.service.mapper.AssignmentMapper;
import com.transportpark.model.service.mapper.BusMapper;
import com.transportpark.model.service.mapper.RouteMapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RouteServiceImpl.class})
public class RouteServiceImplTest {

    private static final Bus BUS = getBus();

    private static final BusEntity BUS_ENTITY = getBusEntity();

    private static final Route ROUTE = getRoute();

    private static final RouteEntity ROUTE_ENTITY = getRouteEntity();

    private static final AssignmentEntity ASSIGNMENT_ENTITY = getAssignmentEntity();

    private static final Assignment ASSIGNMENT = getAssignment();

    private static final List<Assignment> ASSIGNMENTS = Collections.singletonList(ASSIGNMENT);

    private static final List<AssignmentEntity> ASSIGNMENT_ENTITIES = Collections.singletonList(ASSIGNMENT_ENTITY);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @MockBean
    private RouteRepository routeRepository;

    @MockBean
    private AssignmentRepository assignmentRepository;

    @MockBean
    private BusService busService;

    @MockBean
    private RouteMapper routeMapper;

    @MockBean
    private BusMapper busMapper;

    @MockBean
    private AssignmentMapper assignmentMapper;

    @Autowired
    private RouteService service;

    @After
    public void resetMock() {
        reset(routeRepository, assignmentRepository, busService, routeMapper, assignmentMapper, busMapper);
    }

    @Test
    public void shouldAddAssignments() {
        when(busService.findByCode(anyInt())).thenReturn(BUS);

        Assignment assignment = service.addAssignment(ASSIGNMENT);
        assertThat(assignment, is(ASSIGNMENT));
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionWithIncorrectAssignment() {
        exception.expect(InvalidDataRuntimeException.class);

        service.addAssignment(null);
    }

    @Test
    public void shouldAddRoute() {
        when(routeMapper.routeEntityToRoute(any(RouteEntity.class))).thenReturn(ROUTE);
        when(routeMapper.routeToRouteEntity(any(Route.class))).thenReturn(ROUTE_ENTITY);
        when(assignmentMapper.assignmentToAssignmentEntity(any(Assignment.class))).thenReturn(ASSIGNMENT_ENTITY);
        when(routeRepository.save(any(RouteEntity.class))).thenReturn(ROUTE_ENTITY);

        service.addRoute(ASSIGNMENTS);

        verify(assignmentRepository).save(any(AssignmentEntity.class));
    }

    @Test
    public void shouldThrowEntityNotFoundRuntimeExceptionWithInvalidRouteIdInFindAllAssignments() {
        exception.expect(EntityNotFoundRuntimeException.class);
        when(routeRepository.findById(anyLong())).thenThrow(new EntityNotFoundRuntimeException());
        service.findAssignmentsByRoute(-1L);
    }

    @Test
    public void shouldFindAllAssignmentsByRouteId() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ROUTE_ENTITY));
        when(routeMapper.routeEntityToRoute(any(RouteEntity.class))).thenReturn(ROUTE);
        when(routeMapper.routeToRouteEntity(any(Route.class))).thenReturn(ROUTE_ENTITY);
        when(assignmentRepository.findByRoute(any(RouteEntity.class))).thenReturn(Optional.ofNullable(ASSIGNMENT_ENTITY));
        when(assignmentMapper.assignmentEntityToAssignment(any(AssignmentEntity.class))).thenReturn(ASSIGNMENT);

        Assignment assignments = service.findAssignmentsByRoute(ROUTE.getId());

        assertThat(assignments, is(ASSIGNMENT));
    }

    @Test
    public void shouldReturnRoute() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.of(ROUTE_ENTITY));
        when(routeMapper.routeEntityToRoute(any(RouteEntity.class))).thenReturn(ROUTE);

        Route route = service.findById(1L);

        assertThat(route, is(ROUTE));
    }

    @Test
    public void shouldDontFindRoute() {
        when(routeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(routeMapper.routeEntityToRoute(any(RouteEntity.class))).thenReturn(ROUTE);

        Route route = service.findById(-1L);

        assertThat(route, nullValue());
    }

    @Test
    public void shouldThrowAssignmentsNotExistRuntimeExceptionWithNullCancelRouteAssignments() {
        exception.expect(AssignmentsNotExistRuntimeException.class);

        service.cancelRouteAssignments(null, 1);
    }

    @Test
    public void shouldThrowAssignmentsNotExistRuntimeExceptionWithIncorrectCountCancelRouteAssignments() {
        exception.expect(AssignmentsNotExistRuntimeException.class);

        service.cancelRouteAssignments(ASSIGNMENT, -1);
    }

    @Test
    public void shouldCancelRouteAssignmentsByAssignments() {
        when(assignmentMapper.assignmentToAssignmentEntity(any(Assignment.class))).thenReturn(ASSIGNMENT_ENTITY);
        when(routeMapper.routeToRouteEntity(any(Route.class))).thenReturn(ROUTE_ENTITY);

        service.cancelRouteAssignments(ASSIGNMENT, 1);

        verify(assignmentRepository).save(any(AssignmentEntity.class));
        verify(routeRepository).save(any(RouteEntity.class));
    }

    private static RouteEntity getRouteEntity() {
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setId(1L);
        routeEntity.setStatus(0);
        return routeEntity;
    }

    private static Route getRoute() {
        Route route = new Route();
        route.setId(1L);
        route.setStatus(0);
        return route;
    }

    private static AssignmentEntity getAssignmentEntity() {
        AssignmentEntity assignmentEntity = new AssignmentEntity();
        assignmentEntity.setId(1L);
        assignmentEntity.setBus(BUS_ENTITY);
        assignmentEntity.setCanceled(0);
        assignmentEntity.setJourney(10);
        assignmentEntity.setRoute(ROUTE_ENTITY);
        assignmentEntity.setTax(100);
        assignmentEntity.setCanceled(0);
        return assignmentEntity;
    }

    private static Assignment getAssignment() {
        Assignment assignment = new Assignment();
        assignment.setId(1L);
        assignment.setBus(BUS);
        assignment.setCanceled(0);
        assignment.setJourney(10);
        assignment.setRoute(ROUTE);
        assignment.setTax(100);
        assignment.setCanceled(0);
        return assignment;
    }

    private static Bus getBus() {
        Bus bus = new Bus();
        bus.setId(1L);
        bus.setCode(1);
        bus.setStatus("status");
        bus.setModel("model");
        bus.setMileage(10);
        bus.setConsumption(100);
        bus.setComments("comments");
        return bus;
    }

    private static BusEntity getBusEntity() {
        BusEntity busEntity = new BusEntity();
        busEntity.setId(1L);
        busEntity.setCode(1);
        busEntity.setStatus("status");
        busEntity.setModel("model");
        busEntity.setMileage(10);
        busEntity.setConsumption(100);
        busEntity.setComments("comments");
        return busEntity;
    }
}
