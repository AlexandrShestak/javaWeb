package com.shestakam.controller;

import com.shestakam.dao.UserDao;
import com.shestakam.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexandr on 17.7.15.
 */
public class Controller extends HttpServlet {

    private final static Logger logger = Logger.getLogger(Controller.class.getName());
    private UserDao userDao;

    public Controller() {
        this.userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if("getUserForm".equals(action)){
            logger.debug("get user from");
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/pages/addUser.jsp");
            view.forward(request, response);
        }else if("delete".equals(action)){
            logger.debug("delete user");
            String login = request.getParameter("login");
            userDao.delete(login);
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/pages/users.jsp");
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
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/pages/editUser.jsp");
            view.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if("login".equalsIgnoreCase(action)){
            logger.debug("login");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            User user = userDao.get(login);
            if (password.equals(user.getPassword())) {
                RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/pages/users.jsp");
                request.setAttribute("users", userDao.getAll());
                view.forward(request, response);
            } else{
                RequestDispatcher view = request.getRequestDispatcher("index.jsp");
                request.setAttribute("errorMessage","Incorrect login or password");
                view.forward(request,response);
            }
        }else  if("add".equalsIgnoreCase(action)){
            logger.debug("add user");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            userDao.add(user);
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/pages/users.jsp");
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
            userDao.update(user);
            RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/pages/users.jsp");
            request.setAttribute("users", userDao.getAll());
            view.forward(request, response);
        }
    }
}
