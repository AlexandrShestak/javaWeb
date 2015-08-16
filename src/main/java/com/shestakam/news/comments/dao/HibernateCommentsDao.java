package com.shestakam.news.comments.dao;

import com.shestakam.news.comments.entity.Comments;
import com.shestakam.news.entity.News;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 3.8.15.
 */
@Repository
public class HibernateCommentsDao implements CommentsDao {

    private  final static Logger logger = LogManager.getLogger(HibernateCommentsDao.class);

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Comments> getCommentsForNews(Long newsId) {
        logger.debug("get comments for news.news id : " +newsId);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        News news = (News) session.get(News.class,newsId);
        List<Comments> commentsList = new ArrayList<>();
        commentsList.addAll(news.getCommentsSet());
        session.getTransaction().commit();
        session.close();
        return commentsList;
    }

    @Override
    public String save(Comments comments) {
        logger.debug("save comment");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(comments);
        session.getTransaction().commit();
        session.close();
        return String.valueOf(comments.getCommentId());
    }

    @Override
    public Comments get(String id) {
        logger.debug("get comment.id :"+id);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Comments comment = (Comments) session.get(Comments.class , Long.valueOf(id));
        session.getTransaction().commit();
        session.close();
        return  comment;
    }

    @Override
    public List<Comments> getAll() {
        logger.debug("get all comments");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Comments").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void delete(String id) {
        logger.debug("delete comment with id: "+id);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Comments comment = (Comments) session.load(Comments.class,Long.valueOf(id));
        session.delete(comment);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Comments comments) {
        logger.debug("update comment with id : " + comments.getCommentId());
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(comments);
        session.getTransaction().commit();
        session.close();
    }
}
