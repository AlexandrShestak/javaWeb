package com.shestakam.user.dao;

import com.shestakam.db.HibernateUtil;
import com.shestakam.user.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by alexandr on 30.7.15.
 */
public class HibernateUserDao implements UserDao {

    private  final static Logger logger = LogManager.getLogger(HibernateUserDao.class);


    @Override
    public String save(User user) {
        logger.debug("save user");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        return user.getUsername();
    }

    @Override
    public User get(String id) {
        logger.debug("get user");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User user = (User) session.get(User.class,id);
        session.getTransaction().commit();
        return user;
    }

    @Override
    public List<User> getAll() {
        logger.debug("get all users");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from User").list();
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void delete(String id) {
        logger.debug("delete user with username: "+id);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User user = (User) session.load(User.class,id);
        if (user!=null){
            session.delete(user);
        }
        session.getTransaction().commit();
    }

    @Override
    public void update(User user) {
        logger.debug("update user with username: "+user.getUsername() );
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
    }
}
