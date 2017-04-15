package com.shestakam.news.dao;

import com.shestakam.news.entity.News;
import com.shestakam.news.tags.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 2.8.15.
 */
@Repository
public class HibernateNewsDao implements NewsDao {

    private  final static Logger logger = LogManager.getLogger(HibernateNewsDao.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public String save(News news) {
        logger.debug("save news");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(news);
        session.getTransaction().commit();
        session.close();
        return String.valueOf(news.getNewsId());
    }

    @Override
    public News get(String id) {
        logger.debug("get news by id. id = "+id);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        News news = (News) session.get(News.class,Long.valueOf(id));
        session.getTransaction().commit();
        session.close();
        return news;
    }

    @Override
    public List<News> getAll() {
        logger.debug("get all news");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from News ").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void delete(String id) {
        logger.debug("delete news with id: "+id);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        News news = (News) session.load(News.class,Long.valueOf(id));
        session.delete(news);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(News news) {
        logger.debug("update news with id : "+news.getNewsId() );
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(news);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Tag> getTagsForNews(Long newsId) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        News news = (News) session.load(News.class,newsId);
        List<Tag> tagList = new ArrayList<>();
        tagList.addAll(news.getTagSet());
        session.getTransaction().commit();
        session.close();
        return tagList;
    }

    @Override
    public List<News> searchNewsByTag(String tagName) {
        return searchNewsByCreatorAndTag(null,tagName);
        /*Session session = sessionFactory.openSession();
        session.beginTransaction();
        Tag tag = (Tag) session.createQuery("from Tag where tagName=?")
                .setParameter(0,tagName)
                .list()
                .get(0);

        List<News> newsList = new ArrayList<>();
        newsList.addAll(tag.getNewsSet());
        session.getTransaction().commit();
        session.close();
        return newsList;*/
    }

    @Override
    public List<News> searchNewsByCreator(String creator) {
        return searchNewsByCreatorAndTag(creator,null);
        /*Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<News> newsList =  session.createQuery("from News where creatorUsername=?")
                .setParameter(0, creator)
                .list();
        session.getTransaction().commit();
        session.close();
        return newsList;*/
    }

    @Override
    public List<News> searchNewsByCreatorAndTag(String creator, String tagName) {
        if (tagName == null){
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            List<News> newsList= session.createCriteria(News.class)
                    .add(Restrictions.like("creatorUsername", creator))
                    .list();
            session.getTransaction().commit();
            session.close();
            return newsList;
        }
        if (creator == null){
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            List<News> newsList= session.createCriteria(News.class)
                    .createCriteria("tagSet")
                    .add(Restrictions.like("tagName",tagName))
                    .list();
            session.getTransaction().commit();
            session.close();
            return newsList;
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<News> newsList= session.createCriteria(News.class)
                .add(Restrictions.like("creatorUsername",creator))
                .createCriteria("tagSet")
                .add(Restrictions.like("tagName",tagName))
                .list();
        session.getTransaction().commit();
        session.close();
        return newsList;
    }

    @Override
    public void addTagToNews(Long newsId, Long tagId) {
        logger.debug("add tag to news");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        News news = (News) session.load(News.class,newsId);
        Tag tag = (Tag) session.load(Tag.class,tagId);
        news.getTagSet().add(tag);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteTagFromNews(Long newsId, Long tagId) {
        logger.debug("delete tag from news");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        News news = (News) session.load(News.class,newsId);
        Tag tag = (Tag) session.load(Tag.class,tagId);
        news.getTagSet().remove(tag);
        session.getTransaction().commit();
        session.close();
    }
}
