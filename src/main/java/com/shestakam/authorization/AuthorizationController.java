package com.shestakam.authorization;

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
    private UserDao userDao;

    public AuthorizationController() {
        this.userDao = new JdbcUserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String requestUri = request.getRequestURI();
        if(requestUri.equalsIgnoreCase("/login")){
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request,response);
        }else if(requestUri.equalsIgnoreCase("/logout")){
            HttpSession session =  request.getSession(true);
            session.removeAttribute("login");
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        String requestUri = request.getRequestURI();
        if(requestUri.equalsIgnoreCase("/login")){
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request,response);
        }else if(requestUri.equalsIgnoreCase("/logout")){
            HttpSession session =  request.getSession(true);
            session.removeAttribute("login");
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request,response);
        }else if("login".equalsIgnoreCase(action)){
            logger.debug("login");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            User user = userDao.get(login);
            if (password.equals(user.getPassword())) {

                HttpSession session =  request.getSession(true);
                session.setAttribute("login",login);
                RequestDispatcher view = request.getRequestDispatcher("/pages/user/users.jsp");
                request.setAttribute("users", userDao.getAll());
                view.forward(request, response);
            } else{
                RequestDispatcher view = request.getRequestDispatcher("index.jsp");
                request.setAttribute("errorMessage","Incorrect login or password");
                view.forward(request,response);
            }
        }else if ("logout".equalsIgnoreCase(action)){
            HttpSession session =  request.getSession(true);
            session.removeAttribute("login");
            RequestDispatcher view = request.getRequestDispatcher("index.jsp");
            view.forward(request,response);
        }
    }
}
