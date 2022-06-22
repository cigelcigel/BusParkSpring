package com.transportpark.model.service.impl;

import com.transportpark.model.domain.Bus;
import com.transportpark.model.domain.User;
import com.transportpark.model.entity.BusEntity;
import com.transportpark.model.exception.EntityNotFoundRuntimeException;
import com.transportpark.model.exception.InvalidDataRuntimeException;
import com.transportpark.model.repositories.BusRepository;
import com.transportpark.model.service.UserService;
import com.transportpark.model.service.mapper.BusMapper;
import com.transportpark.model.service.mapper.UserMapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BusServiceImpl.class})
public class BusServiceImplTest {

    private static final Bus BUS = getBus();

    private static final User DRIVER = getDriver();

    private static final BusEntity ENTITY = new BusEntity();

    private static final List<BusEntity> BUS_ENTITIES = Collections.singletonList(ENTITY);

    private static final List<Bus> BUSES = Collections.singletonList(BUS);

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @MockBean
    private BusRepository repository;

    @MockBean
    private BusMapper mapper;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Autowired
    private BusServiceImpl service;

    @After
    public void resetMock() {
        reset(repository, mapper, userService, userMapper);
    }

    @Test
    public void shouldReturnBusByCode() {
        when(repository.findByCode(anyInt())).thenReturn(Optional.of(ENTITY));
        when(mapper.busEntityToBus(any(BusEntity.class))).thenReturn(BUS);
        Bus actual = service.findByCode(1);

        verify(repository).findByCode(anyInt());
        verify(mapper).busEntityToBus(any(BusEntity.class));

        assertThat(actual, equalTo(BUS));
    }

    @Test
    public void shouldThrowEntityNotFoundRuntimeExceptionWithNegativeCode() {
        exception.expect(EntityNotFoundRuntimeException.class);
        exception.expectMessage("Don't find bus by this code");

        service.findByCode(-1);
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionExceptionWithEmptyBusInAddBus() {
        exception.expect(InvalidDataRuntimeException.class);
        exception.expectMessage("Invalid input bus data");
        service.addBus(null);
    }

    @Test
    public void shouldSaveBus() {
        when(repository.findByCode(anyInt())).thenReturn(Optional.empty());
        when(mapper.busEntityToBus(any(BusEntity.class))).thenReturn(BUS);
        when(userService.findById(anyLong())).thenReturn(DRIVER);
        Bus actual = Bus.builder()
                .id(1L)
                .code(1)
                .mileage(100)
                .consumption(10)
                .driver(User
                        .builder()
                        .id(1L)
                        .build())
                .build();
        service.addBus(actual);

        verify(repository).save(any());
    }

    @Test
    public void shouldThrowInvalidDataRuntimeExceptionExceptionWithEmptyBusInChangeBus() {
        when(repository.findByCode(anyInt())).thenReturn(Optional.of(ENTITY));
        when(mapper.busEntityToBus(any(BusEntity.class))).thenReturn(BUS);
        exception.expect(InvalidDataRuntimeException.class);
        exception.expectMessage("Invalid input bus data");
        service.changeBus(1, null, null, null);
    }

    @Test
    public void shouldChangeBus() {
        when(repository.findByCode(anyInt())).thenReturn(Optional.of(ENTITY));
        when(mapper.busEntityToBus(any(BusEntity.class))).thenReturn(BUS);

        Bus actual = Bus.builder().id(1L).code(1).mileage(500).consumption(50).build();
        actual.setConsumption(10);
        actual.setMileage(100);
        service.changeBus(1, 100.0, 10.0, 1L);
        assertThat(actual, equalTo(BUS));
        verify(repository).save(any());
    }

    @Test
    public void shouldReturnPagenationBus() {
        PageRequest sortedByCode = PageRequest.of(0, 1, Sort.by("code"));
        when(repository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(BUS_ENTITIES, sortedByCode, 1));
        when(mapper.busEntityToBus(any(BusEntity.class))).thenReturn(BUS);

        Page<Bus> pageBus = service.showPageList(1, 1);

        Page<Bus> actualPageBus = new PageImpl<Bus>(BUSES, sortedByCode, 1);
        assertThat(pageBus, equalTo(actualPageBus));
    }

    private static Bus getBus() {
        return Bus.builder()
                .id(1L)
                .code(1)
                .mileage(100)
                .consumption(10)
                .build();
    }

    private static User getDriver() {
        return User
                .builder()
                .id(1L)
                .build();
    }
}
