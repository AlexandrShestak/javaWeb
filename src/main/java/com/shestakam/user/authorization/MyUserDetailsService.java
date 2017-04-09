package com.shestakam.user.authorization;

import com.shestakam.user.dao.UserDao;
import com.shestakam.user.entity.Role;
import com.shestakam.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 13.8.15.
 */
public class MyUserDetailsService implements UserDetailsService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.get(s);
        List<GrantedAuthority> authorities = new ArrayList();

        for (Role role : userDao.getRoles(s)) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), authorities);
    }
}

