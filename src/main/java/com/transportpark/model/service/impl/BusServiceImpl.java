package com.transportpark.model.service.impl;

import com.transportpark.model.domain.Bus;
import com.transportpark.model.domain.User;
import com.transportpark.model.entity.BusEntity;
import com.transportpark.model.entity.UserEntity;
import com.transportpark.model.exception.EntityNotFoundRuntimeException;
import com.transportpark.model.exception.InvalidDataRuntimeException;
import com.transportpark.model.repositories.BusRepository;
import com.transportpark.model.service.BusService;
import com.transportpark.model.service.UserService;
import com.transportpark.model.service.mapper.BusMapper;
import com.transportpark.model.service.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BusServiceImpl implements BusService {

    private final UserService userService;
    private final BusRepository busRepository;
    private final BusMapper busMapper;
    private final UserMapper userMapper;

    @Override
    public Bus findByCode(int code) {
        return busMapper.busEntityToBus(busRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find bus by this code")));
    }

    @Override
    public Bus findByDriver(User driver) {
        UserEntity userEntity = userMapper.userToUserEntity(driver);
        return busMapper.busEntityToBus(busRepository.findByDriver(userEntity)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find bus by this driver")));
    }

    @Override
    public Page<Bus> showPageList(int currentPage, int pageSize) {
        PageRequest sortedByCode = PageRequest.of(currentPage - 1, pageSize, Sort.by("code"));
        Page<BusEntity> allBusesEntity = busRepository.findAll(sortedByCode);
        List<Bus> result = allBusesEntity
                .stream()
                .map(busMapper::busEntityToBus)
                .collect(Collectors.toList());
        return new PageImpl<>(result, sortedByCode, countAllBuses());
    }

    private long countAllBuses() {
        return busRepository.count();
    }

    @Override
    public void addBus(Bus bus) {
        if (Objects.isNull(bus)) {
            log.warn("Invalid input bus data");
            throw new InvalidDataRuntimeException("Invalid input bus data");
        }
        Optional<BusEntity> busEntity = busRepository.findByCode(bus.getCode());
        if (busEntity.isPresent()) {
            log.warn("Bus with this code is exist");
            throw new InvalidDataRuntimeException("Bus with this code is exist");
        }
        User driver = userService.findById(bus.getDriver().getId());
        bus.setDriver(driver);
        BusEntity entityBus = busMapper.busToBusEntity(bus);
        busRepository.save(entityBus);
    }

    @Override
    public void updateBus(Bus bus) {
        if (Objects.isNull(bus)) {
            log.warn("Invalid input bus data");
            throw new InvalidDataRuntimeException("Invalid input bus data");
        }
        BusEntity entityBus = busMapper.busToBusEntity(bus);
        busRepository.save(entityBus);
    }

    @Override
    public void changeBus(Integer code, Double newMileage, Double newConsumption, Long idDriver) {
        Bus bus = busMapper.busEntityToBus(busRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundRuntimeException("Don't find bus by this code")));
        if (Objects.isNull(newMileage) || Objects.isNull(newConsumption)
                || Objects.isNull(idDriver) || idDriver < 0) {
            log.warn("Invalid input bus data");
            throw new InvalidDataRuntimeException("Invalid input bus data");
        }
        User user = userService.findById(idDriver);
        bus.setDriver(user);
        bus.setMileage(newMileage);
        bus.setConsumption(newConsumption);
        BusEntity busEntity = busMapper.busToBusEntity(bus);
        busRepository.save(busEntity);
    }
}
