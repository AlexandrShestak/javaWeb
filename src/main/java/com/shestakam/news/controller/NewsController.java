package com.shestakam.news.controller;

import com.shestakam.news.dao.NewsDao;
import com.shestakam.news.entity.News;
import com.shestakam.news.tags.dao.TagDao;
import com.shestakam.news.tags.entity.Tag;
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
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by alexandr on 21.7.15.
 */
@Deprecated
public class NewsController extends HttpServlet {

    private static final String NEWS_LIST = "/WEB-INF/pages/news/list.jsp";
    private static final String EDIT_NEWS = "/WEB-INF/pages/news/edit.jsp";
    private static final String ADD_NEWS = "/WEB-INF/pages/news/add.jsp";
    private static final String ADD_TAGS = "/WEB-INF/pages/tags/add.jsp";

    private  final static Logger logger = LogManager.getLogger(NewsController.class);
    private NewsDao newsDao;
    private TagDao tagDao;


    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        tagDao = (TagDao) ac.getBean("hibernateTagsDao");
        newsDao = (NewsDao) ac.getBean("hibernateNewsDao");
    }
   /* public NewsController() {
        this.newsDao= new HibernateNewsDao();
        this.tagDao = new HibernateTagsDao();
    }*/

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)){
            getAddNewsForm(request, response);
        }else if("edit".equals(action)){
            getNewsFormToEdit(request,response);
        }else if("delete".equals(action)){
            deleteNews(request,response);
        }else if("search".equals(action)){
            searchNews(request,response);
        }else if(action == null){
            getNewsForm(request,response);
        }
    }

    private void getNewsForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("get news");
        List<News> newsList = newsDao.getAll();
        request.setAttribute("news", newsList);
        for (News elem: newsList){
            List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
            String tagString = "";
            for(Tag tag: tagList){
                tagString+= "#"+tag.getTagName();
            }
            elem.setTagsString(tagString);
        }
        RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
        view.forward(request, response);
    }

    private void searchNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("search news by tag");
        String tagName = request.getParameter("tag");
        String username = request.getParameter("username");
        if(!tagName.equals("")&&username.equals("")) {
            List<News> newsList = newsDao.searchNewsByTag(tagName);
            request.setAttribute("news", newsList);
            for (News elem : newsList) {
                List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
                String tagString = "";
                for (Tag tag : tagList) {
                    tagString += "#" + tag.getTagName();
                }
                elem.setTagsString(tagString);
            }
        }else if (tagName.equals("")&&!username.equals("")){
            List<News> newsList = newsDao.searchNewsByCreator(username);
            request.setAttribute("news", newsList);
            for (News elem : newsList) {
                List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
                String tagString = "";
                for (Tag tag : tagList) {
                    tagString += "#" + tag.getTagName();
                }
                elem.setTagsString(tagString);
            }
        }else if (!tagName.equals("")&&!username.equals("")){
            List<News> newsList = newsDao.searchNewsByCreatorAndTag(username,tagName);
            request.setAttribute("news", newsList);
            for (News elem : newsList) {
                List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
                String tagString = "";
                for (Tag tag : tagList) {
                    tagString += "#" + tag.getTagName();
                }
                elem.setTagsString(tagString);
            }
        }else {
            logger.error("error with search news");
        }

        RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
        view.forward(request, response);
    }

    private void deleteNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("delete news");
        String newsId = request.getParameter("newsId");
        if(newsId!=null){
            newsDao.delete(newsId);
            List<News> newsList = newsDao.getAll();
            request.setAttribute("news", newsList);
            for (News elem: newsList){
                List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
                String tagString = "";
                for(Tag tag: tagList){
                    tagString+= "#"+tag.getTagName();
                }
                elem.setTagsString(tagString);
            }
            RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
            view.forward(request, response);
        }
    }

    private void getNewsFormToEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("get news form to edit");
        String newsId = request.getParameter("newsId");
        if(newsId!=null){
            News news = newsDao.get(newsId);
            request.setAttribute("news",news);
            List<Tag> tagList = newsDao.getTagsForNews(news.getNewsId());
            String tagString = "";
            for(Tag tag: tagList){
                tagString+= "#"+tag.getTagName();
            }
            news.setTagsString(tagString);
            RequestDispatcher view = request.getRequestDispatcher(EDIT_NEWS);
            view.forward(request, response);
        }
    }

    private void getAddNewsForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("get news form to save");
        RequestDispatcher view = request.getRequestDispatcher(ADD_NEWS);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)){
           addNews(request, response);
        }else if("edit".equals(action)){
            editNews(request,response);
        }
    }

    private void editNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private void addNews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            Tag tag = tagDao.getTagByName(tagName);
            if (tag == null) {
                Long tagId = tag.getTagId();
                logger.debug("new tag");
                Tag tempTag = new Tag();
                tempTag.setTagName(tagName);
                tagId = Long.valueOf(tagDao.save(tempTag));
                newsDao.addTagToNews(Long.valueOf(newsId), tagId);
            } else {
                logger.debug("old tag");
                Long tagId = tag.getTagId();
                newsDao.addTagToNews(Long.valueOf(newsId), tagId);
            }
        }
        List<News> newsList = newsDao.getAll();
        request.setAttribute("news", newsList);
        for (News elem: newsList){
            List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
            String tagString = "";
            for(Tag tag: tagList){
                tagString+= "#"+tag.getTagName();
            }
            elem.setTagsString(tagString);
        }
        RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
        view.forward(request, response);
    }
}
