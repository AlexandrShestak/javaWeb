package com.shestakam.news.tags.dao;

import com.shestakam.db.JdbcConnection;
import com.shestakam.news.tags.entity.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by alexandr on 28.7.15.
 */
public class JdbcTagsDao implements TagDao {

    private  final static Logger logger = LogManager.getLogger(JdbcTagsDao.class);

    public JdbcTagsDao() {
        logger.debug("tags dao constructor");
    }

    @Override
    public String save(Tags tags) {
        int key = 0;
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into tags (tag_name) VALUES (?)",
                            Statement.RETURN_GENERATED_KEYS)) {
            // Parameters start with 1
            preparedStatement.setString(1, tags.getTagName());
            preparedStatement.executeUpdate();
            ResultSet keys = preparedStatement.getGeneratedKeys();
            keys.next();
            key = keys.getInt(1);
            logger.debug("save tag ");
        }
        catch (SQLException e) {
            e.printStackTrace();
            logger.error("save tag error", e);
        }
        return String.valueOf(key);
    }

    @Override
    public Tags get(String id) {
        Tags tag = new Tags();
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from tags where tag_id=?")) {
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                tag.setTagId(rs.getLong("tag_id"));
                tag.setTagName(rs.getString("tag_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get tag error", e);
        }
        logger.debug("get tag with id: "+tag.getTagId());
        return tag;
    }

    @Override
    public List<Tags> getAll() {
        List<Tags> tagsList = new ArrayList<Tags>();
        try (Connection connection = JdbcConnection.getConnection();
             Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("select * from tags");
            while (rs.next()) {
                Tags tag =  new Tags();
                tag.setTagId(rs.getLong("tag_id"));
                tag.setTagName(rs.getString("tag_name"));
                tagsList.add(tag);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get all tags error",e);
        }
        logger.debug("get all tags");
        return tagsList;
    }

    @Override
    public void delete(String id) {
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("delete from tags where tags.tag_id=?")) {
            // Parameters start with 1
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            logger.debug("delete tag with id: "+ id);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("delete tag error",e);
        }
    }

    @Override
    public void update(Tags tags) {
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("update tags set tag_name=? where tag_id=?")) {
            // Parameters start with 1
            preparedStatement.setString(1, tags.getTagName());
            preparedStatement.setLong(2,tags.getTagId());
            preparedStatement.executeUpdate();
            logger.debug("edit tag with id :" + tags.getTagId());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("edit tag error",e);
        }
    }

    @Override
    public Long getTagIdByName(String tagName) {
        Long tagId = null;
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * from tags where tag_name=?)",
                            Statement.RETURN_GENERATED_KEYS)) {
            // Parameters start with 1
            preparedStatement.setString(1, tagName);
            ResultSet rs = preparedStatement.executeQuery();

            Tags tag = new Tags();
            if (rs.next()) {
                tag.setTagId(rs.getLong("tag_id"));
                tag.setTagName(rs.getString("tag_name"));
            }
            tagId = tag.getTagId();
            logger.error("get tag id by tag name ");
        }
        catch (SQLException e) {
            e.printStackTrace();
            logger.error("get tag id by tag name error", e);
        }
        return tagId;
    }

    @Override
    public void addTagToNews(Long newsId, Long tagId) {
        int key = 0;
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("insert into news_tags (news_id , tag_id) VALUES (?,?)")){
            // Parameters start with 1
            preparedStatement.setLong(1, newsId);
            preparedStatement.setLong(2, tagId);
            preparedStatement.executeUpdate();

            logger.error("add tag to news");
        }
        catch (SQLException e) {
            e.printStackTrace();
            logger.error("add tag to news error", e);
        }
    }
}
