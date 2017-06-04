package com.shestakam.news.tags.controller;

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
import java.util.List;

/**
 * Created by alexandr on 4.8.15.
 */
@Deprecated
public class TagController extends HttpServlet {

    private static final String EDIT_NEWS = "/WEB-INF/pages/news/edit.jsp";
    private static final String NEWS_LIST = "/WEB-INF/pages/news/list.jsp";

    private  final static Logger logger = LogManager.getLogger(TagController.class);


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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)){
            addTag(request,response);
        }else if("delete".equals(action)){
            deleteTag(request,response);
        }
    }

    private void deleteTag(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("delete tag");
        String tagName  = request.getParameter("tagName");
        String newsId = request.getParameter("newsId");
        List<Tag> tagListForNews = newsDao.getTagsForNews(Long.valueOf(newsId));
        Tag tempTag =  tagDao.getTagByName(tagName);
        if (tagListForNews.contains(tempTag)) {
            newsDao.deleteTagFromNews(Long.valueOf(newsId), tagDao.getTagByName(tagName).getTagId());
        }
        News news = newsDao.get(newsId);
        List<Tag> tagList = newsDao.getTagsForNews(news.getNewsId());
        String tagString = "";
        for(Tag tag: tagList){
            tagString+= "#"+tag.getTagName();
        }
        news.setTagsString(tagString);
        RequestDispatcher view = request.getRequestDispatcher(EDIT_NEWS);
        view.forward(request, response);
    }

    private void addTag(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tagName  = request.getParameter("tagName");
        String newsId = request.getParameter("newsId");
        logger.debug("add tag");
        Tag tag = tagDao.getTagByName(tagName);
        if (tag == null) {
            logger.debug("new tag");
            Tag tempTag = new Tag();
            tempTag.setTagName(tagName);
            Long tagId = Long.valueOf(tagDao.save(tempTag));
            newsDao.addTagToNews(Long.valueOf(newsId), tagId);
        } else {
            logger.debug("old tag");
            Long tagId = tag.getTagId();
            newsDao.addTagToNews(Long.valueOf(newsId), tagId);
        }
        News news = newsDao.get(newsId);
        request.setAttribute("news",news);
        List<Tag> tagList = newsDao.getTagsForNews(news.getNewsId());
        String tagString = "";
        for(Tag elemTag: tagList){
            tagString+= "#"+elemTag.getTagName();
        }
        news.setTagsString(tagString);
        RequestDispatcher view = request.getRequestDispatcher(EDIT_NEWS);
        view.forward(request, response);
    }

    //TODO: fix get method to tag
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("get news from");
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
