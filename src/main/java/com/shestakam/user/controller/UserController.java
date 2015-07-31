package com.shestakam.user.controller;

import com.shestakam.news.dao.JdbcNewsDao;
import com.shestakam.news.dao.NewsDao;
import com.shestakam.user.dao.HibernateUserDao;
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

    private static final String USERS_LIST = "/WEB-INF/pages/user/list.jsp";
    private static final String REGISTRATION_PAGE= "/WEB-INF/pages/authorization/registration.jsp";
    private static final String START_PAGE = "index.jsp";
    private static final String ADD_USER_PAGE = "/WEB-INF/pages/user/add.jsp";
    private static final String EDIT_USER_PAGE = "/WEB-INF/pages/user/edit.jsp";
    private static final String NEWS_LIST = "/WEB-INF/pages/news/list.jsp";

    private  final static Logger logger = LogManager.getLogger(UserController.class);
    private UserDao userDao;
    private NewsDao newsDao;

    public UserController() {
        this.userDao = new HibernateUserDao();
        this.newsDao = new JdbcNewsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String login =  request.getParameter("username");

        if("add".equals(action)){
            logger.debug("get user form");
            RequestDispatcher view = request.getRequestDispatcher(ADD_USER_PAGE);
            view.forward(request, response);
        }else if("delete".equals(action) && login!=null){
            logger.debug("delete user");
            userDao.delete(login);
            RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
            request.setAttribute("news",newsDao.getAll());
            view.forward(request, response);

        }else if("edit".equals(action) && login!=null){
            logger.debug("return edit user form");
            User user  = userDao.get(login);
            request.setAttribute("user",user);
            RequestDispatcher view = request.getRequestDispatcher(EDIT_USER_PAGE);
            view.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equalsIgnoreCase(action)) {
            logger.debug("save user");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            userDao.save(user);
            RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
            request.setAttribute("news", newsDao.getAll());
            view.forward(request, response);
        } else if ("edit".equals(action)) {
            logger.debug("edit user");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            userDao.update(user);
            RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
            request.setAttribute("news", newsDao.getAll());
            view.forward(request, response);
        }
    }
}
