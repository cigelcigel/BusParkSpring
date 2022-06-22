package com.transportpark.model.service.mapper;

import com.transportpark.model.domain.User;
import com.transportpark.model.domain.UserType;
import com.transportpark.model.entity.UserEntity;
import com.transportpark.model.entity.UserTypeEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserMapper {

    private UserTypeMapper userTypeMapper;

    public User userEntityToUser(UserEntity userEntity) {
        if (Objects.isNull(userEntity)) {
            return null;
        }

        UserType userType = userTypeMapper.userTypeEntityToUserType(userEntity.getUserType());

        return User.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .name(userEntity.getName())
                .userType(userType)
                .build();
    }

    public UserEntity userToUserEntity(User user) {
        if (Objects.isNull(user)) {
            return null;
        }

        UserTypeEntity userType = userTypeMapper.userTypeToUserTypeEntity(user.getUserType());
        return UserEntity.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .userType(userType)
                .build();
    }
}
