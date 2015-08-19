package com.shestakam.user.dao;

import com.shestakam.db.GenericDao;
import com.shestakam.user.entity.Role;
import com.shestakam.user.entity.User;

import java.util.Set;

/**
 * Created by alexandr on 19.7.15.
 */
public interface UserDao extends GenericDao<User> {
    Set<Role> getRoles(String username);
    void addRole(String username,String role);
}
