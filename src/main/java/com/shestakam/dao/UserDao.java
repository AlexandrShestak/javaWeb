package com.shestakam.dao;

import com.shestakam.entity.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 17.7.15.
 */
public class UserDao{

    private final static Logger logger = Logger.getLogger(UserDao.class.getName());
    private Connection connection;

    public UserDao() {
        this.connection = JdbcConnection.getConnection();
        logger.debug("user dao constructor");
    }

    public void add(User user){

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("insert into user (login,password,email) VALUES (?,?,?)");
            // Parameters start with 1
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
            logger.error("add user error");
        }
        catch (SQLException e) {
            e.printStackTrace();
            logger.error("add user error", e);
        }
    }

    public void delete(String login) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from user where login=?");
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
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update user set password=?, email=?"
                    + "where uname=?");
            // Parameters start with 1
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getEmail());
            logger.debug("update user with login :"+user.getLogin());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("update user error",e);
        }
    }

    public User get(String login) {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user where login=?");
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("get user error",e);
        }
        logger.debug("get user with login :"+user.getLogin());
        return user;
    }


    public List<User> getAll() {
        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users");
            while (rs.next()) {
                User user = new User();
                user.setLogin(rs.getString("login"));
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
