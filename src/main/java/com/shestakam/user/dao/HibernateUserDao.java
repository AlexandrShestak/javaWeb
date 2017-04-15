package com.shestakam.user.dao;

import com.shestakam.user.entity.Role;
import com.shestakam.user.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by alexandr on 30.7.15.
 */

@Repository
public class HibernateUserDao implements UserDao {

    private  final static Logger logger = LogManager.getLogger(HibernateUserDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public String save(User user) {
        logger.debug("save user");
        /*Session session = sessionFactory.getCurrentSession();*/
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
        return user.getUsername();
    }

    @Override
    public User get(String id) {
        logger.debug("get user");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = (User) session.get(User.class,id);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() {
        logger.debug("get all users");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void delete(String id) {
        logger.debug("delete user with username: "+id);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = (User) session.load(User.class,id);
        if (user!=null){
            session.delete(user);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(User user) {
        logger.debug("update user with username: "+user.getUsername() );
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Set<Role> getRoles(String username) {
        logger.debug("get roles  for "+ username );
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = (User) session.get(User.class,username);
        Set<Role> roles = user.getRoleSet();
        session.getTransaction().commit();
        session.close();
        return roles;
    }

    @Override
    public void addRole(String username, String roleName) {
        logger.debug("add role  for "+ username );
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = (User) session.get(User.class,username);
        Role role = (Role) session.createQuery("from Role where role=?")
                .setParameter(0, roleName)
                .list()
                .get(0);
        user.getRoleSet().add(role);
        session.getTransaction().commit();
        session.close();
        return ;
    }
}
