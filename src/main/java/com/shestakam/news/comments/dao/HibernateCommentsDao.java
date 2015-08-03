package com.shestakam.news.comments.dao;

import com.shestakam.db.HibernateUtil;
import com.shestakam.news.comments.entity.Comments;
import com.shestakam.news.entity.News;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 3.8.15.
 */
public class HibernateCommentsDao implements CommentsDao {

    private  final static Logger logger = LogManager.getLogger(HibernateCommentsDao.class);



    @Override
    public List<Comments> getCommentsForNews(Long newsId) {
        logger.debug("get comments for news.news id : " +newsId);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        News news = (News) session.get(News.class,newsId);
        List<Comments> commentsList = new ArrayList<>();
        commentsList.addAll(news.getCommentsSet());
        session.getTransaction().commit();
        return commentsList;
    }

    @Override
    public String save(Comments comments) {
        logger.debug("save comment");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(comments);
        session.getTransaction().commit();
        return String.valueOf(comments.getCommentId());
    }

    @Override
    public Comments get(String id) {
        logger.debug("get comment.id :"+id);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Comments comment = (Comments) session.get(Comments.class , Long.valueOf(id));
        session.getTransaction().commit();
        return  comment;
    }

    @Override
    public List<Comments> getAll() {
        logger.debug("get all comments");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from Comments").list();
        session.getTransaction().commit();
        return result;
    }

    @Override
    public void delete(String id) {
        logger.debug("delete comment with id: "+id);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Comments comment = (Comments) session.load(Comments.class,Long.valueOf(id));
        session.delete(comment);
        session.getTransaction().commit();
    }

    @Override
    public void update(Comments comments) {
        logger.debug("update comment with id : " + comments.getCommentId());
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.update(comments);
        session.getTransaction().commit();
    }
}
