package com.shestakam.user.controller;

import com.shestakam.user.dao.JdbcUserDao;
import com.shestakam.user.dao.UserDao;
import com.shestakam.user.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by alexandr on 18.7.15.
 */
public class UserController extends HttpServlet {

    private  final static Logger logger = LogManager.getLogger(UserController.class);
    private UserDao userDao;

    public UserController() {
        this.userDao = new JdbcUserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if("getUserForm".equals(action)){
            logger.debug("get user form");
            RequestDispatcher view = request.getRequestDispatcher("//pages/user/addUser.jsp");
            view.forward(request, response);
        }else if("delete".equals(action)){
            logger.debug("delete user");
            String login = request.getParameter("login");
            userDao.delete(login);
            RequestDispatcher view = request.getRequestDispatcher("/pages/user/users.jsp");
            request.setAttribute("users", userDao.getAll());
            view.forward(request, response);

        }else if("edit".equals(action)){
            logger.debug("return edit user form");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            request.setAttribute("user",user);
            RequestDispatcher view = request.getRequestDispatcher("/pages/user/editUser.jsp");
            view.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if("add".equalsIgnoreCase(action)){
            logger.debug("add user");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            userDao.add(user);
            RequestDispatcher view = request.getRequestDispatcher("/pages/user/users.jsp");
            request.setAttribute("users", userDao.getAll());
            view.forward(request, response);

        }else if("edit".equals(action)){
            logger.debug("edit user");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            userDao.edit(user);
            RequestDispatcher view = request.getRequestDispatcher("/pages/user/users.jsp");
            request.setAttribute("users", userDao.getAll());
            view.forward(request, response);
        }
    }
}