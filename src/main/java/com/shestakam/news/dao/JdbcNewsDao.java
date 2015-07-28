package com.shestakam.news.dao;

import com.shestakam.db.JdbcConnection;
import com.shestakam.news.entity.News;
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
 * Created by alexandr on 20.7.15.
 */
public class JdbcNewsDao implements NewsDao {

    private  final static Logger logger = LogManager.getLogger(JdbcNewsDao.class);


    public JdbcNewsDao() {
        logger.debug("news dao constructor");
    }

    @Override
    public String add(News news) {
        int key = 0;
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into news (news_text, creation_date, creator_username) VALUES (?,?,?)",
                            Statement.RETURN_GENERATED_KEYS)) {
            // Parameters start with 1
            preparedStatement.setString(1, news.getNewsText());
            preparedStatement.setTimestamp(2, news.getCreationDate());
            preparedStatement.setString(3,news.getCreatorUsername());
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            keys.next();
            key = keys.getInt(1);
            logger.error("add news ");
        }
        catch (SQLException e) {
            e.printStackTrace();
            logger.error("add news error", e);
        }
        return String.valueOf(key);
    }

    @Override
    public News get(String id) {
        News news = new News();
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from news where news_id=?")) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                news.setNewsId(rs.getLong("news_id"));
                news.setCreationDate(rs.getTimestamp("creation_date"));
                news.setNewsText(rs.getString("news_text"));
                news.setCreatorUsername(rs.getString("creator_username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get news error", e);
        }
        logger.debug("get news with id: "+news.getNewsId());
        return news;
    }

    @Override
    public List<News> getAll() {

        List<News> newsList = new ArrayList<News>();
        try (Connection connection = JdbcConnection.getConnection();
             Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("select * from news");
            while (rs.next()) {
                News news = new News();
                news.setNewsId(rs.getLong("news_id"));
                news.setCreationDate(rs.getTimestamp("creation_date"));
                news.setNewsText(rs.getString("news_text"));
                news.setCreatorUsername(rs.getString("creator_username"));
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get all news error",e);
        }
        logger.debug("get all news");
        return newsList;
    }

    @Override
    public void delete(String id) {
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("delete from news where news_id=?")) {
            // Parameters start with 1
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            logger.debug("delete news with id: "+ id);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("delete news error",e);
        }
    }

    @Override
    public void update(News news) {
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("update news set news_text=?,creation_date=? where news_id=?")){
            // Parameters start with 1
            preparedStatement.setString(1, news.getNewsText());
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setLong(3, news.getNewsId());
            preparedStatement.executeUpdate();
            logger.debug("edit news with id :" + news.getNewsId());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("edit news error",e);
        }
    }
}
