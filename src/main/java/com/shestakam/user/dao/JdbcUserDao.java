package com.shestakam.user.dao;

import com.shestakam.db.JdbcConnection;
import com.shestakam.user.entity.User;
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
 * Created by alexandr on 17.7.15.
 */
public class JdbcUserDao implements UserDao {

    private  final static Logger logger = LogManager.getLogger(JdbcUserDao.class);


    public JdbcUserDao() {
        logger.debug("user dao constructor");
    }

    public String save(User user){
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement  preparedStatement =
                    connection.prepareStatement("insert into users (username,password,email) VALUES (?,?,?)")) {
            // Parameters start with 1
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
            logger.debug("save user");
        }
        catch (SQLException e) {
            e.printStackTrace();
            logger.error("save user error", e);
        }
        return user.getUsername();
    }

    public void delete(String login) {
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("delete from users where users.username=?")) {
            // Parameters start with 1
            preparedStatement.setString(1, login);
            preparedStatement.executeUpdate();
            logger.debug("delete user with login: "+ login);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("delete user error",e);
        }
    }

    public void update(User user) {
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("update users set password=?, email=? where users.username=?")) {
            // Parameters start with 1
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.executeUpdate();
            logger.debug("edit user with login :"+user.getUsername());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("edit user error",e);
        }
    }

    public User get(String login) {
        User user = new User();
        try(Connection connection = JdbcConnection.getConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("select * from users where users.username=?")) {
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get user error", e);
        }
        logger.debug("get user with login :"+user.getUsername());
        return user;
    }


    public List<User> getAll() {
        List<User> users = new ArrayList<User>();
        try (Connection connection = JdbcConnection.getConnection();
             Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("select * from users");
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get all users error",e);
        }
        logger.debug("get all users");
        return users;
    }
}
