package com.transportpark.model.service.mapper;

import com.transportpark.model.domain.UserType;
import com.transportpark.model.entity.UserTypeEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserTypeMapper {

    public UserType userTypeEntityToUserType(UserTypeEntity userTypeEntity) {

        return Objects.isNull(userTypeEntity) ?
                null :
                UserType.builder()
                        .id(userTypeEntity.getId())
                        .type(userTypeEntity.getType())
                        .description(userTypeEntity.getDescription())
                        .build();
    }

    public UserTypeEntity userTypeToUserTypeEntity(UserType userType) {

        return Objects.isNull(userType) ?
                null :
                UserTypeEntity.builder()
                        .id(userType.getId())
                        .type(userType.getType())
                        .description(userType.getDescription())
                        .build();
    }
}
