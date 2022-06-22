package com.transportpark.model.service.mapper;

import com.transportpark.model.domain.Route;
import com.transportpark.model.entity.RouteEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RouteMapper.class})
public class RouteMapperTest {
    private static final RouteEntity ROUTE_ENTITY = getRouteEntity();

    private static final Route ROUTE = getRoute();

    @Autowired
    private RouteMapper routeMapper;

    @Test
    public void shouldMapCheckEntityToCheck() {
        Route actual = routeMapper.routeEntityToRoute(ROUTE_ENTITY);

        assertThat(actual.getId(), is(ROUTE.getId()));
        assertThat(actual.getStatus(), is(ROUTE.getStatus()));
    }

    @Test
    public void shouldMapCheckToCheckEntity() {
        RouteEntity actual = routeMapper.routeToRouteEntity(ROUTE);

        assertThat(actual.getId(), is(ROUTE_ENTITY.getId()));
        assertThat(actual.getStatus(), is(ROUTE_ENTITY.getStatus()));
    }

    @Test
    public void mapCheckToCheckEntityShouldReturnNull() {
        RouteEntity actual = routeMapper.routeToRouteEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapCheckEntityToCheckShouldReturnNull() {
        Route actual = routeMapper.routeEntityToRoute(null);

        assertThat(actual, is(nullValue()));
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
}
