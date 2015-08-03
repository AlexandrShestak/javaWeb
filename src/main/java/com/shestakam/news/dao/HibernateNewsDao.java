package com.shestakam.news.dao;

import com.shestakam.db.HibernateUtil;
import com.shestakam.news.entity.News;
import com.shestakam.news.tags.entity.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 2.8.15.
 */
public class HibernateNewsDao implements NewsDao {

    private  final static Logger logger = LogManager.getLogger(HibernateNewsDao.class);


    @Override
    public String save(News news) {
        logger.debug("save news");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(news);
        session.getTransaction().commit();
        return String.valueOf(news.getNewsId());
    }

    @Override
    public News get(String id) {
        logger.debug("get news by id. id = "+id);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        News news = (News) session.get(News.class,Long.valueOf(id));
        session.getTransaction().commit();
        return news;

    }

    @Override
    public List<News> getAll() {
        logger.debug("get all news");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from News ").list();
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void delete(String id) {
        logger.debug("delete news with id: "+id);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        News news = (News) session.load(News.class,Long.valueOf(id));
        session.delete(news);
        session.getTransaction().commit();
    }

    @Override
    public void update(News news) {
        logger.debug("update news with id : "+news.getNewsId() );
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(news);
        session.getTransaction().commit();
    }

    @Override
    public List<Tags> getTagsForNews(Long newsId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        News news = (News) session.load(News.class,newsId);
        List<Tags> tagsList = new ArrayList<>();
        tagsList.addAll(news.getTagsSet());
        session.getTransaction().commit();
        return tagsList;
    }

    @Override
    public List<News> searchNewsByTag(String tagName) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Tags tag = (Tags) session.createQuery("from Tags where tagName=?")
                .setParameter(0,tagName)
                .list()
                .get(0);

        List<News> newsList = new ArrayList<>();
        newsList.addAll(tag.getNewsSet());
        session.getTransaction().commit();
        return newsList;
    }
}
