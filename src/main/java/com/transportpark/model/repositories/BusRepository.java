package com.transportpark.model.repositories;

import com.transportpark.model.entity.BusEntity;
import com.transportpark.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRepository extends JpaRepository<BusEntity, Long> {

    Optional<BusEntity> findByCode(int code);

    Optional<BusEntity> findByDriver(UserEntity driver);
}
