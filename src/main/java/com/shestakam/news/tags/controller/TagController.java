package com.shestakam.news.tags.controller;

import com.shestakam.news.dao.HibernateNewsDao;
import com.shestakam.news.dao.NewsDao;
import com.shestakam.news.entity.News;
import com.shestakam.news.tags.dao.HibernateTagsDao;
import com.shestakam.news.tags.dao.TagDao;
import com.shestakam.news.tags.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by alexandr on 4.8.15.
 */
public class TagController extends HttpServlet {

    private static final String EDIT_NEWS = "/WEB-INF/pages/news/edit.jsp";
    private static final String NEWS_LIST = "/WEB-INF/pages/news/list.jsp";

    private  final static Logger logger = LogManager.getLogger(TagController.class);


    private NewsDao newsDao;
    private TagDao tagDao;

    public TagController() {
        this.newsDao= new HibernateNewsDao();
        this.tagDao = new HibernateTagsDao();
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
        Tag tempTag =  tagDao.get(String.valueOf(tagDao.getTagIdByName(tagName)));
        if (tagListForNews.contains(tempTag)) {
            tagDao.deleteTagFromNews(Long.valueOf(newsId), tagDao.getTagIdByName(tagName));
        }
        News news = newsDao.get(newsId);
        request.setAttribute("news",news);
        List<Tag> tagList = newsDao.getTagsForNews(news.getNewsId());
        String tagString = new String();
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
        Long tagId = tagDao.getTagIdByName(tagName);
        if (tagId == null) {
            logger.debug("new tag");
            Tag tag = new Tag();
            tag.setTagName(tagName);
            tagId = Long.valueOf(tagDao.save(tag));
            tagDao.addTagToNews(Long.valueOf(newsId), tagId);
        } else {
            logger.debug("old tag");
            tagDao.addTagToNews(Long.valueOf(newsId), tagId);
        }
        News news = newsDao.get(newsId);
        request.setAttribute("news",news);
        List<Tag> tagList = newsDao.getTagsForNews(news.getNewsId());
        String tagString = new String();
        for(Tag tag: tagList){
            tagString+= "#"+tag.getTagName();
        }
        news.setTagsString(tagString);
        RequestDispatcher view = request.getRequestDispatcher(EDIT_NEWS);
        view.forward(request, response);
    }

    //TODO: fix get method to tag
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("i");
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
        RequestDispatcher view = request.getRequestDispatcher(NEWS_LIST);
        view.forward(request, response);
    }
}
