package com.shestakam.news.tags.controller;

import com.shestakam.news.tags.dao.JdbcTagsDao;
import com.shestakam.news.tags.dao.TagDao;
import com.shestakam.news.tags.entity.Tags;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexandr on 28.7.15.
 */
public class TagController extends HttpServlet {

    private static final String NEWS_LIST = "/pages/news/list.jsp";

    private  final static Logger logger = LogManager.getLogger(TagController.class);


    private TagDao tagDao = new JdbcTagsDao();

    public TagController() {
        tagDao = new JdbcTagsDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)){
            logger.debug("add tag");
            String newsId = request.getParameter("newsId");
            String tagName = request.getParameter("tagName");
            Long tagId = tagDao.getTagIdByName(tagName);
            if (tagId == null){
                logger.debug("new tag");
                Tags tag = new Tags();
                tag.setTagName(tagName);
                tagId = Long.valueOf(tagDao.save(tag));
                tagDao.addTagToNews(Long.valueOf(newsId),tagId);
            }else{
                logger.debug("old tag");
                tagDao.addTagToNews(Long.valueOf(newsId),tagId);
            }

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
