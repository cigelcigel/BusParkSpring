package com.transportpark.model.service;

import com.transportpark.model.domain.Bus;
import com.transportpark.model.domain.User;
import org.springframework.data.domain.Page;

public interface BusService {

    void updateBus(Bus bus);

    Bus findByDriver(User driver);

    Bus findByCode(int code);

    Page<Bus> showPageList(int currentPage, int pageSize);

    void addBus(Bus bus);

    void changeBus(Integer code, Double newMileage, Double newConsumption, Long idDriver);
}
