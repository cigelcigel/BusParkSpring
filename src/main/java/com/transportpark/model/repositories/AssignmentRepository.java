package com.transportpark.model.repositories;

import com.transportpark.model.entity.AssignmentEntity;
import com.transportpark.model.entity.BusEntity;
import com.transportpark.model.entity.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, Long> {

    Optional<AssignmentEntity> findByRoute(RouteEntity route);

    Optional<AssignmentEntity> findByBus(BusEntity bus);
}
