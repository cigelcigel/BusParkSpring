package com.transportpark.model.service.mapper;

import com.transportpark.model.domain.Assignment;
import com.transportpark.model.domain.Bus;
import com.transportpark.model.domain.Route;
import com.transportpark.model.entity.AssignmentEntity;
import com.transportpark.model.entity.BusEntity;
import com.transportpark.model.entity.RouteEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssignmentMapper {

    private RouteMapper routeMapper;
    private BusMapper busMapper;

    public Assignment assignmentEntityToAssignment(AssignmentEntity assignmentEntity) {
        if (Objects.isNull(assignmentEntity)) {
            return null;
        }

        Route route = routeMapper.routeEntityToRoute(assignmentEntity.getRoute());
        Bus bus = busMapper.busEntityToBus(assignmentEntity.getBus());

        return Assignment.builder()
                .id(assignmentEntity.getId())
                .tax(assignmentEntity.getTax())
                .journey(assignmentEntity.getJourney())
                .canceled(assignmentEntity.getCanceled())
                .route(route)
                .bus(bus)
                .build();
    }

    public AssignmentEntity assignmentToAssignmentEntity(Assignment assignment) {
        if (Objects.isNull(assignment)) {
            return null;
        }

        RouteEntity route = routeMapper.routeToRouteEntity(assignment.getRoute());
        BusEntity busEntity = busMapper.busToBusEntity(assignment.getBus());

        return AssignmentEntity.builder()
                .id(assignment.getId())
                .tax(assignment.getTax())
                .journey(assignment.getJourney())
                .canceled(assignment.getCanceled())
                .route(route)
                .bus(busEntity)
                .build();
    }
}
