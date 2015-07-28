package com.shestakam.comments.dao;

import com.shestakam.comments.entity.Comments;
import com.shestakam.db.JdbcConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 24.7.15.
 */
public class JdbcCommentsDao implements CommentsDao {

    private  final static Logger logger = LogManager.getLogger(JdbcCommentsDao.class);

    public JdbcCommentsDao() {
        logger.debug("comments dao constructor");
    }

    @Override
    public String add(Comments comments) {
        int key = 0;
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                connection.prepareStatement("insert into comments " +
                        "(comment_text, creation_date, commentator_username , news_id) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            // Parameters start with 1
            preparedStatement.setString(1, comments.getCommentText());
            preparedStatement.setTimestamp(2, comments.getCreationDate());
            preparedStatement.setString(3, comments.getCommentatorUsername());
            preparedStatement.setLong(4, comments.getNewsId());
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            keys.next();
            key = keys.getInt(1);
            logger.error("add comments ");
        }
        catch (SQLException e) {
            e.printStackTrace();
            logger.error("add comment error", e);
        }
        return String.valueOf(key);

    }

    @Override
    public Comments get(String id) {
        Comments comment = new Comments();
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from comments where comment_id=?")) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                comment.setCommentId(rs.getLong("comment_id"));
                comment.setNewsId(rs.getLong("news_id"));
                comment.setCommentatorUsername(rs.getString("commentator_username"));
                comment.setCommentText(rs.getString("comment_text"));
                comment.setCreationDate(rs.getTimestamp("creation_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get comment error", e);
        }
        logger.debug("get comment with id: "+comment.getCommentId());
        return comment;
    }

    @Override
    public List<Comments> getAll() {
        List<Comments> commentsList = new ArrayList<Comments>();
        try (Connection connection = JdbcConnection.getConnection();
            Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("select * from comments");
            while (rs.next()) {
                Comments comment = new Comments();
                comment.setCommentId(rs.getLong("comment_id"));
                comment.setNewsId(rs.getLong("news_id"));
                comment.setCommentatorUsername(rs.getString("commentator_username"));
                comment.setCommentText(rs.getString("comment_text"));
                comment.setCreationDate(rs.getTimestamp("creation_date"));
                commentsList.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get all comments error",e);
        }
        logger.debug("get all comments");
        return commentsList;
    }


    @Override
    public void delete(String id) {
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("delete from comments where comments.comment_id=?")) {
            // Parameters start with 1
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            logger.debug("delete comment with id: "+ id);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("delete comment error",e);
        }
    }
    @Override
    public void update(Comments comments) {
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("update comments set comment_text=?,creation_date=? where comment_id=?")) {
            // Parameters start with 1
            preparedStatement.setString(1, comments.getCommentText());
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setLong(3,comments.getCommentId());
            preparedStatement.executeUpdate();
            logger.debug("edit comment with id :" + comments.getCommentId());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("edit comment error",e);
        }
    }

    @Override
    public List<Comments> getCommentsForNews(Long newsId) {
        List<Comments> commentsList = new ArrayList<Comments>();
        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("select * from comments where news_id=?")){

            preparedStatement.setLong(1,newsId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Comments comment = new Comments();
                comment.setCommentId(rs.getLong("comment_id"));
                comment.setNewsId(rs.getLong("news_id"));
                comment.setCommentatorUsername(rs.getString("commentator_username"));
                comment.setCommentText(rs.getString("comment_text"));
                comment.setCreationDate(rs.getTimestamp("creation_date"));
                commentsList.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get all comments for news error.  news id: "+ newsId,e);
        }
        logger.debug("get all comments for news. news id: "+ newsId);
        return commentsList;
    }
}
