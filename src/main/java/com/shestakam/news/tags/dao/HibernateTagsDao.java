package com.shestakam.news.tags.dao;

import com.shestakam.news.tags.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by alexandr on 3.8.15.
 */
@Repository
public class HibernateTagsDao implements TagDao {

    private  final static Logger logger = LogManager.getLogger(HibernateTagsDao.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Tag getTagByName(String tagName) {
        logger.debug("get tag id by name");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result =  session.createQuery("from Tag where tagName=?")
                .setParameter(0,tagName)
                .list();
        if(result.size() == 0) {
            session.getTransaction().commit();
            return null;
        }
        Tag tag = (Tag) result.get(0);
        session.getTransaction().commit();
        session.close();
        return tag;
    }



    @Override
    public String save(Tag tag) {
        logger.debug("save tag");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(tag);
        session.getTransaction().commit();
        session.close();
        return String.valueOf(tag.getTagId());
    }

    @Override
    public Tag get(String id) {
        logger.debug("get tag by id. id = "+id);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Tag tag = (Tag) session.get(Tag.class,Long.valueOf(id));
        session.getTransaction().commit();
        session.close();
        return tag;
    }

    @Override
    public List<Tag> getAll() {
        logger.debug("get all tags");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Tag").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void delete(String id) {
        logger.debug("delete tag with id: "+id);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Tag tag = (Tag) session.load(Tag.class,Long.valueOf(id));
        session.delete(tag);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Tag tag) {
        logger.debug("update tag with id : "+ tag.getTagId() );
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(tag);
        session.getTransaction().commit();
        session.close();
    }
}
