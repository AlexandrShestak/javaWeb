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
       /* String toRedirect = request.getHeader("referer");
        String host = request.getHeader("host");
        toRedirect = toRedirect.replace(host,"").replace("http://","").replace("language?language=en","").replace("language?language=ru","");
        RequestDispatcher view = request.getRequestDispatcher(toRedirect);*/
     //   view.forward(request,response);
        response.sendRedirect( request.getHeader("referer"));

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
    /*    String toRedirect = request.getHeader("referer");
        String host = request.getHeader("host");
        toRedirect = toRedirect.replace(host,"").replace("http://","").replace("language?language=en","").replace("language?language=ru","");
        RequestDispatcher view = request.getRequestDispatcher(toRedirect);
     */  // view.forward(request,response);
        response.sendRedirect( request.getHeader("referer"));

    }
}
