package com.shestakam.authorization;

import com.shestakam.news.dao.JdbcNewsDao;
import com.shestakam.news.dao.NewsDao;
import com.shestakam.user.dao.JdbcUserDao;
import com.shestakam.user.entity.User;
import com.shestakam.user.dao.UserDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Created by alexandr on 17.7.15.
 */
public class AuthorizationController extends HttpServlet {

    private  final static Logger logger = LogManager.getLogger(AuthorizationController.class);
    private static final String USERS_LIST = "/pages/user/users.jsp";
    private static final String START_PAGE = "index.jsp";
    private static final String NEWS_LIST = "/pages/news/news.jsp";

    private UserDao userDao;
    private NewsDao newsDao;

    public AuthorizationController() {

        this.userDao = new JdbcUserDao();
        this.newsDao = new JdbcNewsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String requestUri = request.getRequestURI();
        if(requestUri.equalsIgnoreCase("/login")){
            String login = (String) request.getSession().getAttribute("login");
            if( login == null){
                RequestDispatcher view = request.getRequestDispatcher(START_PAGE);
                view.forward(request,response);
            }else {
                RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
                request.setAttribute("news", newsDao.getAll());
                view.forward(request, response);
            }

        }else if(requestUri.equalsIgnoreCase("/logout")){
            HttpSession session =  request.getSession(true);
            session.removeAttribute("login");
            RequestDispatcher view = request.getRequestDispatcher(START_PAGE);
            view.forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        String requestUri = request.getRequestURI();
        if(requestUri.equalsIgnoreCase("/login")){
            logger.debug("login");
            String login = request.getParameter("login");
            login = new String(login.getBytes("iso-8859-1"), "UTF-8");
            String password = request.getParameter("password");
            User user = userDao.get(login);
            if (password.equals(user.getPassword())) {

                HttpSession session =  request.getSession(true);
                session.setAttribute("login",login);
                RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
                request.setAttribute("news",newsDao.getAll());
                view.forward(request, response);
            } else{
                RequestDispatcher view = request.getRequestDispatcher(START_PAGE);
                request.setAttribute("errorMessage","Incorrect login or password");
                view.forward(request,response);
            }
        }else if(requestUri.equalsIgnoreCase("/logout")){
            HttpSession session =  request.getSession(true);
            session.removeAttribute("login");
            RequestDispatcher view = request.getRequestDispatcher(START_PAGE);
            view.forward(request,response);
        }
    }
}
