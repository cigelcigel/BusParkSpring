package com.transportpark.model.repositories;

import com.transportpark.model.entity.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTypeRepository extends JpaRepository<UserTypeEntity, Long> {

    Optional<UserTypeEntity> findByType(String type);
}
