package com.shestakam.news.tags.dao;

import com.shestakam.db.HibernateUtil;
import com.shestakam.news.entity.News;
import com.shestakam.news.tags.entity.Tags;
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
    public Long getTagIdByName(String tagName) {
        logger.debug("get tag id by name");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List result =  session.createQuery("from Tags where tagName=?")
                .setParameter(0,tagName)
                .list();
        if(result.size() == 0)
            return null;
        Tags tag = (Tags) result.get(0);
        Long tagId  = tag.getTagId();
        session.getTransaction().commit();
        return tagId;
    }

    @Override
    public void addTagToNews(Long newsId, Long tagId) {
        logger.debug("add tag to news");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        News news = (News) session.load(News.class,newsId);
        Tags tag = (Tags) session.load(Tags.class,tagId);
        news.getTagsSet().add(tag);
        session.getTransaction().commit();
    }

    @Override
    public String save(Tags tags) {
        logger.debug("save tag");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(tags);
        session.getTransaction().commit();
        return String.valueOf(tags.getTagId());
    }

    @Override
    public Tags get(String id) {
        logger.debug("get tag by id. id = "+id);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Tags tag = (Tags) session.get(Tags.class,Long.valueOf(id));
        session.getTransaction().commit();
        return tag;
    }

    @Override
    public List<Tags> getAll() {
        logger.debug("get all tags");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from Tags").list();
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void delete(String id) {
        logger.debug("delete tag with id: "+id);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Tags tag = (Tags) session.load(Tags.class,Long.valueOf(id));
        session.delete(tag);
        session.getTransaction().commit();
    }

    @Override
    public void update(Tags tags) {
        logger.debug("update tag with id : "+ tags.getTagId() );
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(tags);
        session.getTransaction().commit();
    }
}
