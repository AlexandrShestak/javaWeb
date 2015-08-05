package com.shestakam.news.dao;

import com.shestakam.db.JdbcConnection;
import com.shestakam.news.entity.News;
import com.shestakam.news.tags.entity.Tag;
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
    public String save(News news) {
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
            logger.error("save news ");
        }
        catch (SQLException e) {
            e.printStackTrace();
            logger.error("save news error", e);
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

    @Override
    public List<Tag> getTagsForNews(Long newsId) {
        List<Tag> tagList = new ArrayList<Tag>();
        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement psToFindTagsId =
                     connection.prepareStatement("select * from news_tags where news_id=?");
             PreparedStatement psToFindTags =
                     connection.prepareStatement("select * from tags where tag_id=?")){

            psToFindTagsId.setLong(1, newsId);
            ResultSet rs = psToFindTagsId.executeQuery();

            while (rs.next()) {
                psToFindTags.setLong(1, rs.getLong("tag_id"));
                ResultSet rsWithTags = psToFindTags.executeQuery();
                while(rsWithTags.next()){
                    Tag tag = new Tag();
                    tag.setTagId(rsWithTags.getLong("tag_id"));
                    tag.setTagName(rsWithTags.getString("tag_name"));
                    tagList.add(tag);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get get tags for news error.  news id: "+ newsId,e);
        }
        logger.debug("get tags for news. news id: "+ newsId);
        return tagList;
    }

    @Override
    public List<News> searchNewsByTag(String tagName) {
        List<News> newsList = new ArrayList<News>();
        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement psToFindNewsId =
                     connection.prepareStatement("SELECT  news_id from news_tags INNER JOIN tags on news_tags.tag_id=tags.tag_id where tag_name=?");
             PreparedStatement psToFindNews =
                     connection.prepareStatement("select * from news where news_id=?")){

            psToFindNewsId.setString(1,tagName);
            ResultSet rs = psToFindNewsId.executeQuery();

            while (rs.next()) {
                psToFindNews.setLong(1, rs.getLong("news_id"));
                ResultSet rsWithNews = psToFindNews.executeQuery();
                while(rsWithNews.next()){
                    News news = new News();
                    news.setNewsId(rsWithNews.getLong("news_id"));
                    news.setCreationDate(rsWithNews.getTimestamp("creation_date"));
                    news.setNewsText(rsWithNews.getString("news_text"));
                    news.setCreatorUsername(rsWithNews.getString("creator_username"));
                    newsList.add(news);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get all news with tag: "+ tagName+" error",e);
        }
        logger.debug("get all news with tag: "+ tagName);
        return newsList;
    }

    @Override
    public List<News> searchNewsByCreator(String creator) {
        List<News> newsList = new ArrayList<News>();
        try (Connection connection = JdbcConnection.getConnection();
                    PreparedStatement psToFindNews =
                     connection.prepareStatement("select * from news where creator_username=?")){
                psToFindNews.setString(1, creator);
                ResultSet rsWithNews = psToFindNews.executeQuery();
                while(rsWithNews.next()){
                    News news = new News();
                    news.setNewsId(rsWithNews.getLong("news_id"));
                    news.setCreationDate(rsWithNews.getTimestamp("creation_date"));
                    news.setNewsText(rsWithNews.getString("news_text"));
                    news.setCreatorUsername(rsWithNews.getString("creator_username"));
                    newsList.add(news);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get all news with creator: "+ creator+" error",e);
        }
        logger.debug("get all news with creator: "+ creator);
        return newsList;
    }

    @Override
    public List<News> searchNewsByCreatorAndTag(String creator, String tagName) {
        List<News> newsList = new ArrayList<News>();
        try (Connection connection = JdbcConnection.getConnection();
             PreparedStatement psToFindNewsId =
                     connection.prepareStatement("SELECT  news_id from news_tags INNER JOIN tags on news_tags.tag_id=tags.tag_id where tag_name=?");
             PreparedStatement psToFindNews =
                     connection.prepareStatement("select * from news where news_id=? AND creator_username=?")){

            psToFindNewsId.setString(1,tagName);
            ResultSet rs = psToFindNewsId.executeQuery();
            while (rs.next()) {
                psToFindNews.setLong(1, rs.getLong("news_id"));
                psToFindNews.setString(1, creator);
                ResultSet rsWithNews = psToFindNews.executeQuery();
                while(rsWithNews.next()){
                    News news = new News();
                    news.setNewsId(rsWithNews.getLong("news_id"));
                    news.setCreationDate(rsWithNews.getTimestamp("creation_date"));
                    news.setNewsText(rsWithNews.getString("news_text"));
                    news.setCreatorUsername(rsWithNews.getString("creator_username"));
                    newsList.add(news);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get all news with tag: "+ tagName+" error",e);
        }

        logger.debug("get all news with tag: "+ tagName);
        return newsList;
    }
}
