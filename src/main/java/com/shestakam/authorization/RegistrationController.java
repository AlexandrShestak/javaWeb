package com.shestakam.authorization;

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
 * Created by alexandr on 20.7.15.
 */
public class RegistrationController extends HttpServlet {

    private  final static Logger logger = LogManager.getLogger(RegistrationController.class);
    private UserDao userDao;

    public RegistrationController() {
        this.userDao = new JdbcUserDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if("registration".equalsIgnoreCase(action)){
            logger.debug("user registration");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            User user = new User();
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            if(userDao.get(login).getLogin()!=null){
                request.setAttribute("errorMessage","Пользователь с таким именем уже существует");
                RequestDispatcher view = request.getRequestDispatcher("/pages/user/registration.jsp");
                view.forward(request, response);
            }else {
                userDao.add(user);
                RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
                view.forward(request, response);
            }

        }
    }
}
