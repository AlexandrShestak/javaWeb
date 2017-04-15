package com.shestakam.user.authorization;

import com.shestakam.user.dao.UserDao;
import com.shestakam.user.entity.Role;
import com.shestakam.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 13.8.15.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    @Qualifier("hibernateUserDao")
    private UserDao userDao;

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

