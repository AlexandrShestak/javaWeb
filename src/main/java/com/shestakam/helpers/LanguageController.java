package com.shestakam.helpers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexandr on 21.7.15.
 */
public class LanguageController extends HttpServlet {

    private static final String START_PAGE = "index.jsp";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String language = request.getParameter("language");
        if ("en".equalsIgnoreCase(language)){
            request.getSession().setAttribute("language","en");
        }else  if ("ru".equalsIgnoreCase(language)){
            request.getSession().setAttribute("language","ru");
        }
        RequestDispatcher view = request.getRequestDispatcher(START_PAGE);
        view.forward(request,response);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String language = request.getParameter("language");
        if ("en".equalsIgnoreCase(language)){
            request.getSession().setAttribute("language","en");
        }else  if ("ru".equalsIgnoreCase(language)){
            request.getSession().setAttribute("language","ru");
            //response.setContentType("text/html;charset=UTF-8");
        }
        RequestDispatcher view = request.getRequestDispatcher(START_PAGE);
        view.forward(request,response);



    }
}
