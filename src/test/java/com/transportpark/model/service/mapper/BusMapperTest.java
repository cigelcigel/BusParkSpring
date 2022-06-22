package com.transportpark.model.service.mapper;

import com.transportpark.model.domain.Bus;
import com.transportpark.model.entity.BusEntity;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.reset;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BusMapper.class})
public class BusMapperTest {
    private static final BusEntity BUS_ENTITY = getBusEntity();

    private static final Bus BUS = getBus();

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private BusMapper busMapper;

    @After
    public void resetMock() {
        reset(userMapper);
    }

    @Test
    public void shouldMapGoodEntityToGood() {
        BusEntity actual = busMapper.busToBusEntity(BUS);

        assertThat(actual.getId(), is(BUS_ENTITY.getId()));
        assertThat(actual.getCode(), is(BUS_ENTITY.getCode()));
        assertThat(actual.getModel(), is(BUS_ENTITY.getModel()));
        assertThat(actual.getMileage(), is(BUS_ENTITY.getMileage()));
        assertThat(actual.getConsumption(), is(BUS_ENTITY.getConsumption()));
        assertThat(actual.getComments(), is(BUS_ENTITY.getComments()));
        assertThat(actual.getStatus(), is(BUS_ENTITY.getStatus()));
    }

    @Test
    public void shouldMapGoodToGoodEntity() {
        Bus actual = busMapper.busEntityToBus(BUS_ENTITY);

        assertThat(actual.getId(), is(BUS.getId()));
        assertThat(actual.getCode(), is(BUS.getCode()));
        assertThat(actual.getModel(), is(BUS.getModel()));
        assertThat(actual.getMileage(), is(BUS.getMileage()));
        assertThat(actual.getConsumption(), is(BUS.getConsumption()));
        assertThat(actual.getComments(), is(BUS.getComments()));
        assertThat(actual.getStatus(), is(BUS.getStatus()));
    }

    @Test
    public void mapGoodToGoodEntityShouldReturnNull() {
        BusEntity actual = busMapper.busToBusEntity(null);

        assertThat(actual, is(nullValue()));
    }

    @Test
    public void mapGoodEntityToGoodShouldReturnNull() {
        Bus actual = busMapper.busEntityToBus(null);

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
}
