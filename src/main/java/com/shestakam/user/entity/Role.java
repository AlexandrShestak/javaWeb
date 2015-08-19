package com.shestakam.user.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alexandr on 19.8.15.
 */
public class Role {

    private Long id;
    private String role;
    private Set<User> usersSet = new HashSet<>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<User> getUsersSet() {
        return usersSet;
    }

    public void setUsersSet(Set<User> usersSet) {
        this.usersSet = usersSet;
    }
}
