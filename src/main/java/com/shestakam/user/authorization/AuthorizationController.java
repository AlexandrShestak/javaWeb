package com.shestakam.user.authorization;

import com.shestakam.news.dao.HibernateNewsDao;
import com.shestakam.news.dao.NewsDao;
import com.shestakam.news.entity.News;
import com.shestakam.news.tags.entity.Tag;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


/**
 * Created by alexandr on 17.7.15.
 */
public class AuthorizationController extends HttpServlet {

    private  final static Logger logger = LogManager.getLogger(AuthorizationController.class);
    private static final String USERS_LIST = "/WEB-INF/pages/user/list.jsp";
    private static final String START_PAGE = "index.jsp";
    private static final String NEWS_LIST = "/WEB-INF/pages/news/list.jsp";

    private UserDao userDao;
    private NewsDao newsDao;

    public AuthorizationController() {

        this.userDao = new HibernateUserDao();
        this.newsDao = new HibernateNewsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String requestUri = request.getRequestURI();
        if(requestUri.equalsIgnoreCase("/login")){
            String login = (String) request.getSession().getAttribute("login");
            if( login == null){
                RequestDispatcher view = request.getRequestDispatcher(START_PAGE);
                view.forward(request,response);
            }else {
                RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
                List<News> newsList = newsDao.getAll();
                request.setAttribute("news", newsList);
                for (News elem: newsList){
                    List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
                    String tagString = new String();
                    for(Tag tag: tagList){
                        tagString+= "#"+tag.getTagName();
                    }
                    elem.setTagsString(tagString);
                }
                view.forward(request, response);
            }

        }else if(requestUri.equalsIgnoreCase("/logout")){
            HttpSession session =  request.getSession(true);
            session.removeAttribute("login");
            session.invalidate();
            RequestDispatcher view = request.getRequestDispatcher(START_PAGE);
            view.forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        String requestUri = request.getRequestURI();
        if(requestUri.equalsIgnoreCase("/login")){
            logger.debug("login");
            String login = request.getParameter("login");
            login = new String(login.getBytes("iso-8859-1"), "UTF-8");
            String password = request.getParameter("password");
            User user = userDao.get(login);
            if (user==null){
                RequestDispatcher view = request.getRequestDispatcher(START_PAGE);
                request.setAttribute("errorMessage","Incorrect login or password");
                view.forward(request,response);
            }
            if (password.equals(user.getPassword())) {

                HttpSession session =  request.getSession(true);
                session.setAttribute("login",login);
                RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
                List<News> newsList = newsDao.getAll();
                request.setAttribute("news", newsList);
                for (News elem: newsList){
                    List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
                    String tagString = new String();
                    for(Tag tag: tagList){
                        tagString+= "#"+tag.getTagName();
                    }
                    elem.setTagsString(tagString);
                }
                view.forward(request, response);
            } else{
                RequestDispatcher view = request.getRequestDispatcher(START_PAGE);
                request.setAttribute("errorMessage","Incorrect login or password");
                view.forward(request,response);
            }
        }else if(requestUri.equalsIgnoreCase("/logout")){
            HttpSession session =  request.getSession(true);
            session.removeAttribute("login");
            session.invalidate();
            RequestDispatcher view = request.getRequestDispatcher(START_PAGE);
            view.forward(request,response);
        }
    }
}
