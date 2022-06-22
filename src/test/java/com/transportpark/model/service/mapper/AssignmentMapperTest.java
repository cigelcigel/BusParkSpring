package com.transportpark.model.service.mapper;

import com.transportpark.model.domain.Assignment;
import com.transportpark.model.domain.Bus;
import com.transportpark.model.domain.Route;
import com.transportpark.model.entity.AssignmentEntity;
import com.transportpark.model.entity.BusEntity;
import com.transportpark.model.entity.RouteEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {AssignmentMapper.class})
public class AssignmentMapperTest {
    private static final RouteEntity ROUTE_ENTITY = getRouteEntity();

    private static final Route ROUTE = getRoute();

    private static final BusEntity BUS_ENTITY = getBusEntity();

    private static final Bus BUS = getBus();

    private static final AssignmentEntity ASSIGNMENT_ENTITY = getAssignmentEntity();

    private static final Assignment ASSIGNMENT = getAssignment();

    @MockBean
    private RouteMapper routeMapper;

    @MockBean
    private BusMapper busMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Test
    public void shouldMapOrderEntityToOrder() {
        when(routeMapper.routeEntityToRoute(any(RouteEntity.class))).thenReturn(ROUTE);
        when(busMapper.busEntityToBus(any(BusEntity.class))).thenReturn(BUS);

        Assignment actual = assignmentMapper.assignmentEntityToAssignment(ASSIGNMENT_ENTITY);

        assertThat(actual.getId(), is(ASSIGNMENT.getId()));
        assertThat(actual.getTax(), is(ASSIGNMENT.getTax()));
        assertThat(actual.getCanceled(), is(ASSIGNMENT.getCanceled()));
        assertThat(actual.getJourney(), is(ASSIGNMENT.getJourney()));
        assertThat(actual.getBus(), is(ASSIGNMENT.getBus()));
        assertThat(actual.getRoute(), is(ASSIGNMENT.getRoute()));
    }

    @Test
    public void shouldMapOrderToOrderEntity() {
        when(routeMapper.routeToRouteEntity(any(Route.class))).thenReturn(ROUTE_ENTITY);
        when(busMapper.busToBusEntity(any(Bus.class))).thenReturn(BUS_ENTITY);

        AssignmentEntity actual = assignmentMapper.assignmentToAssignmentEntity(ASSIGNMENT);

        assertThat(actual.getId(), is(ASSIGNMENT_ENTITY.getId()));
        assertThat(actual.getTax(), is(ASSIGNMENT_ENTITY.getTax()));
        assertThat(actual.getCanceled(), is(ASSIGNMENT_ENTITY.getCanceled()));
        assertThat(actual.getJourney(), is(ASSIGNMENT_ENTITY.getJourney()));
        assertThat(actual.getBus(), is(ASSIGNMENT_ENTITY.getBus()));
        assertThat(actual.getRoute(), is(ASSIGNMENT_ENTITY.getRoute()));
    }

    @Test
    public void mapGoodToGoodEntityShouldReturnNull() {
        AssignmentEntity actual = assignmentMapper.assignmentToAssignmentEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapGoodEntityToGoodShouldReturnNull() {
        Assignment actual = assignmentMapper.assignmentEntityToAssignment(null);

        assertThat(actual, is(nullValue()));
    }

    private static BusEntity getBusEntity() {
        return BusEntity.builder()
                .id(1L)
                .code(1)
                .model("model")
                .mileage(100)
                .consumption(10)
                .status("status")
                .comments("comments")
                .build();
    }

    private static Bus getBus() {
        return Bus.builder()
                .id(1L)
                .code(1)
                .model("model")
                .mileage(100)
                .consumption(10)
                .status("status")
                .comments("comments")
                .build();
    }

    private static RouteEntity getRouteEntity() {
        return new RouteEntity(1L, null, 0, null);
    }

    private static Route getRoute() {
        return Route.builder()
                .id(1L)
                .status(0)
                .build();
    }

    private static AssignmentEntity getAssignmentEntity() {
        return new AssignmentEntity(1L, 100.0, 1000, 0,
                ROUTE_ENTITY, BUS_ENTITY);
    }

    private static Assignment getAssignment() {
        return Assignment.builder()
                .id(1L)
                .tax(100)
                .journey(1000)
                .canceled(0)
                .route(ROUTE)
                .bus(BUS)
                .build();
    }
}
