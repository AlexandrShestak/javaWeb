package com.shestakam.news.tags.dao;

import com.shestakam.db.HibernateUtil;
import com.shestakam.news.tags.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by alexandr on 3.8.15.
 */
public class HibernateTagsDao implements TagDao {

    private  final static Logger logger = LogManager.getLogger(HibernateTagsDao.class);

    @Override
    public Tag getTagByName(String tagName) {
        logger.debug("get tag id by name");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
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
        return tag;
    }



    @Override
    public String save(Tag tag) {
        logger.debug("save tag");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(tag);
        session.getTransaction().commit();
        return String.valueOf(tag.getTagId());
    }

    @Override
    public Tag get(String id) {
        logger.debug("get tag by id. id = "+id);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Tag tag = (Tag) session.get(Tag.class,Long.valueOf(id));
        session.getTransaction().commit();
        return tag;
    }

    @Override
    public List<Tag> getAll() {
        logger.debug("get all tags");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from Tag").list();
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void delete(String id) {
        logger.debug("delete tag with id: "+id);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Tag tag = (Tag) session.load(Tag.class,Long.valueOf(id));
        session.delete(tag);
        session.getTransaction().commit();
    }

    @Override
    public void update(Tag tag) {
        logger.debug("update tag with id : "+ tag.getTagId() );
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(tag);
        session.getTransaction().commit();
    }
}
