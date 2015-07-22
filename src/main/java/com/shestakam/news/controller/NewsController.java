package com.shestakam.news.controller;

import com.shestakam.news.dao.JdbcNewsDao;
import com.shestakam.news.dao.NewsDao;
import com.shestakam.news.entity.News;
import com.shestakam.user.dao.JdbcUserDao;
import com.shestakam.user.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Created by alexandr on 21.7.15.
 */
public class NewsController extends HttpServlet {

    private static final String NEWS_LIST = "/pages/news/news.jsp";
    private static final String EDIT_NEWS = "/pages/news/editNews.jsp";
    private static final String ADD_NEWS = "/pages/news/addNews.jsp";

    private  final static Logger logger = LogManager.getLogger(NewsController.class);
    private NewsDao newsDao;

    public NewsController() {
        this.newsDao= new JdbcNewsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)){
            RequestDispatcher view = request.getRequestDispatcher(ADD_NEWS);
            view.forward(request, response);
        }else if("edit".equals(action)){
            String newsId = request.getParameter("newsId");
            if(newsId!=null){
                News news = newsDao.get(newsId);
                request.setAttribute("news",news);
                RequestDispatcher view = request.getRequestDispatcher(EDIT_NEWS);
                view.forward(request, response);
            }
        }else if("delete".equals(action)){
            String newsId = request.getParameter("newsId");
            if(newsId!=null){
                newsDao.delete(newsId);
                request.setAttribute("news",newsDao.getAll());
                RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
                view.forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)){
            String newsId = request.getParameter("newsId");
            String newsText = request.getParameter("newsText");
            Date newsCreationDate = Date.valueOf(request.getParameter("creationDate"));
            String newsCommentator = (String) request.getSession().getAttribute("login");
            News news = new News();
            news.setNewsId(Long.valueOf(newsId));
            news.setUserLogin(newsCommentator);
            news.setCreationDate(newsCreationDate);
            news.setNewsText(newsText);
            newsDao.add(news);

            request.setAttribute("news",newsDao.getAll());
            RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
            view.forward(request, response);
        }else if("edit".equals(action)){
            String newsId = request.getParameter("newsId");
            String newsText = request.getParameter("newsText");
            Date newsCreationDate = Date.valueOf(request.getParameter("creationDate"));
            String newsCommentator = (String) request.getSession().getAttribute("login");
            News news = new News();
            news.setNewsId(Long.valueOf(newsId));
            news.setUserLogin(newsCommentator);
            news.setCreationDate(newsCreationDate);
            news.setNewsText(newsText);
            newsDao.edit(news);

            request.setAttribute("news",newsDao.getAll());
            RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
            view.forward(request, response);
        }
    }
}
