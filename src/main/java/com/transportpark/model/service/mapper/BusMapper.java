package com.transportpark.model.service.mapper;

import com.transportpark.model.domain.Bus;
import com.transportpark.model.domain.User;
import com.transportpark.model.entity.BusEntity;
import com.transportpark.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BusMapper {
    private UserMapper userMapper;

    public Bus busEntityToBus(BusEntity busEntity) {
        if (Objects.isNull(busEntity)) {
            return null;
        }

        User driver = userMapper.userEntityToUser(busEntity.getDriver());

        return Bus.builder()
                .id(busEntity.getId())
                .code(busEntity.getCode())
                .model(busEntity.getModel())
                .mileage(busEntity.getMileage())
                .consumption(busEntity.getConsumption())
                .status(busEntity.getStatus())
                .comments(busEntity.getComments())
                .driver(driver)
                .build();
    }

    public BusEntity busToBusEntity(Bus bus) {

        if (Objects.isNull(bus)) {
            return null;
        }

        UserEntity driver = userMapper.userToUserEntity(bus.getDriver());

        return BusEntity.builder()
                .id(bus.getId())
                .code(bus.getCode())
                .model(bus.getModel())
                .mileage(bus.getMileage())
                .consumption(bus.getConsumption())
                .status(bus.getStatus())
                .comments(bus.getComments())
                .driver(driver)
                .build();
    }
}
