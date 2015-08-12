package com.shestakam.user.controller;

import com.shestakam.user.dao.UserDao;
import com.shestakam.user.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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
    private static final String ADD_USER_PAGE = "/WEB-INF/pages/user/add.jsp";
    private static final String EDIT_USER_PAGE = "/WEB-INF/pages/user/edit.jsp";

    private  final static Logger logger = LogManager.getLogger(UserController.class);
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        userDao = (UserDao) ac.getBean("hibernateUsersDao");
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String login =  request.getParameter("username");

        if("add".equals(action)){
            getAddUserForm(request,response);
        }else if("delete".equals(action) && login!=null){
           deleteUser(request,response);

        }else if("edit".equals(action) && login!=null){
           getEditUserForm(request, response);
        }else if(action == null){
          getAllUsers(request,response);
        }
    }

    private void getAllUsers(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher(USERS_LIST);
        request.setAttribute("users", userDao.getAll());
        view.forward(request, response);
    }

    private void getEditUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("return edit user form");
        String login =  request.getParameter("username");
        User user  = userDao.get(login);
        request.setAttribute("user",user);
        RequestDispatcher view = request.getRequestDispatcher(EDIT_USER_PAGE);
        view.forward(request, response);
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("delete user");
        String login =  request.getParameter("username");
        userDao.delete(login);
        RequestDispatcher view = request.getRequestDispatcher(USERS_LIST);
        request.setAttribute("users", userDao.getAll());
        view.forward(request, response);
    }

    private void getAddUserForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("get user form");
        RequestDispatcher view = request.getRequestDispatcher(ADD_USER_PAGE);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equalsIgnoreCase(action)) {
           addUser(request, response);
        } else if ("edit".equals(action)) {
            editUser(request,response);
        }
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("edit user");
        String login = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        User user = new User();
        user.setUsername(login);
        user.setPassword(password);
        user.setEmail(email);
        userDao.update(user);
        RequestDispatcher view = request.getRequestDispatcher(USERS_LIST);
        request.setAttribute("users", userDao.getAll());
        view.forward(request, response);
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("add user");
        String login = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        User user = new User();
        user.setUsername(login);
        user.setPassword(password);
        user.setEmail(email);
        userDao.save(user);
        RequestDispatcher view = request.getRequestDispatcher(USERS_LIST);
        request.setAttribute("users", userDao.getAll());
        view.forward(request, response);
    }
}
