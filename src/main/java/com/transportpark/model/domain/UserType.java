package com.transportpark.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@Builder

public class UserType implements GrantedAuthority {
    private Long id;

    private String type;

    private String description;

    private List<User> users;

    @Override
    public String getAuthority() {
        return type;
    }
}
