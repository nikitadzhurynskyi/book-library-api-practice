package com.kiyotaka.booklibraryapipractice.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role_entity")
public class RoleEntity implements GrantedAuthority {
    @Id
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> userEntities;

    public RoleEntity(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
