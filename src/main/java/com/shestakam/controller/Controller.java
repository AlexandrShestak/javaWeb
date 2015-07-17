package com.shestakam.controller;

import com.shestakam.dao.UserDao;
import com.shestakam.entity.User;

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

    private UserDao userDao;

    public Controller() {
        this.userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action.equalsIgnoreCase("")){

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String act = request.getParameter("action");
        if(act.equalsIgnoreCase("login")){
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
        }
    }
}
