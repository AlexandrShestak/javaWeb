package com.shestakam.news.controller;

import com.shestakam.news.dao.JdbcNewsDao;
import com.shestakam.news.dao.NewsDao;
import com.shestakam.news.entity.News;
import com.shestakam.news.tags.dao.JdbcTagsDao;
import com.shestakam.news.tags.dao.TagDao;
import com.shestakam.news.tags.entity.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

/**
 * Created by alexandr on 21.7.15.
 */
public class NewsController extends HttpServlet {

    private static final String NEWS_LIST = "/pages/news/list.jsp";
    private static final String EDIT_NEWS = "/pages/news/edit.jsp";
    private static final String ADD_NEWS = "/pages/news/add.jsp";
    private static final String ADD_TAGS = "/pages/tags/add.jsp";

    private  final static Logger logger = LogManager.getLogger(NewsController.class);
    private NewsDao newsDao;
    private TagDao tagDao;

    public NewsController() {
        this.newsDao= new JdbcNewsDao();
        this.tagDao = new JdbcTagsDao();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)){
            logger.debug("get news form to save");
            RequestDispatcher view = request.getRequestDispatcher(ADD_NEWS);
            view.forward(request, response);
        }else if("edit".equals(action)){
            logger.debug("get news form to edit");
            String newsId = request.getParameter("newsId");
            if(newsId!=null){
                News news = newsDao.get(newsId);
                request.setAttribute("news",news);
                RequestDispatcher view = request.getRequestDispatcher(EDIT_NEWS);
                view.forward(request, response);
            }
        }else if("delete".equals(action)){
            logger.debug("delete news");
            String newsId = request.getParameter("newsId");
            if(newsId!=null){
                newsDao.delete(newsId);
                request.setAttribute("news",newsDao.getAll());
                RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
                view.forward(request, response);
            }
        }else if("search".equals(action)){
            logger.debug("search news by tag");
            String tag = request.getParameter("tag");
            request.setAttribute("news",newsDao.searchNewsByTag(tag));
            RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
            view.forward(request, response);

        }else if(action == null){
            logger.debug("get news");
            request.setAttribute("news",newsDao.getAll());
            RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
            view.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)){
            logger.debug("save news");
            String newsText = request.getParameter("newsText");
            newsText = new String(newsText.getBytes("iso-8859-1"), "UTF-8");
            Timestamp newsCreationDate = new Timestamp(System.currentTimeMillis());
            String newsCommentator = (String) request.getSession().getAttribute("login");
            News news = new News();
            news.setCreatorUsername(newsCommentator);
            news.setCreationDate(newsCreationDate);
            news.setNewsText(newsText);
            String newsId = newsDao.save(news);
            String[] tags  = request.getParameter("tags").split(";");
            for(String tagName : tags) {
                logger.debug("add tag");
                Long tagId = tagDao.getTagIdByName(tagName);
                if (tagId == null) {
                    logger.debug("new tag");
                    Tags tag = new Tags();
                    tag.setTagName(tagName);
                    tagId = Long.valueOf(tagDao.save(tag));
                    tagDao.addTagToNews(Long.valueOf(newsId), tagId);
                } else {
                    logger.debug("old tag");
                    tagDao.addTagToNews(Long.valueOf(newsId), tagId);
                }
            }
            request.setAttribute("news",newsDao.getAll());
            //request.setAttribute("newsId",newsId);
            RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
            view.forward(request, response);
        }else if("edit".equals(action)){
            logger.debug("edit news");
            String newsId = request.getParameter("newsId");
            String newsText = request.getParameter("newsText");
            newsText = new String(newsText.getBytes("iso-8859-1"), "UTF-8");
            Timestamp newsCreationDate = new Timestamp(System.currentTimeMillis());
            String newsCommentator = (String) request.getSession().getAttribute("login");
            News news = new News();
            news.setNewsId(Long.valueOf(newsId));
            news.setCreatorUsername(newsCommentator);
            news.setCreationDate(newsCreationDate);
            news.setNewsText(newsText);
            newsDao.update(news);
            request.setAttribute("news",newsDao.getAll());
            RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
            view.forward(request, response);
        }
    }
}
