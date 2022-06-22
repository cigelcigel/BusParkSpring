package com.transportpark.model.service.mapper;

import com.transportpark.model.domain.Route;
import com.transportpark.model.entity.RouteEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RouteMapper {

    public Route routeEntityToRoute(RouteEntity routeEntity) {

        return Objects.isNull(routeEntity) ?
                null :
                Route.builder()
                        .id(routeEntity.getId())
                        .registerTime(routeEntity.getRegisterTime())
                        .status(routeEntity.getStatus())
                        .build();
    }

    public RouteEntity routeToRouteEntity(Route route) {

        return Objects.isNull(route) ?
                null :
                RouteEntity.builder()
                        .id(route.getId())
                        .registerTime(route.getRegisterTime())
                        .status(route.getStatus())
                        .build();
    }
}
