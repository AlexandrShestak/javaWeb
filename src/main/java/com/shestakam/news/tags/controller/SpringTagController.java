package com.shestakam.news.tags.controller;

import com.shestakam.news.dao.NewsDao;
import com.shestakam.news.entity.News;
import com.shestakam.news.tags.dao.TagDao;
import com.shestakam.news.tags.entity.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by alexandr on 15.8.15.
 */
@Controller
public class SpringTagController {
    private static final String EDIT_NEWS = "news/edit";
    private static final String NEWS_LIST = "news/list";

    private  final static Logger logger = LogManager.getLogger(SpringTagController.class);


    private NewsDao newsDao;
    private TagDao tagDao;

    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }


    @RequestMapping(value = "/tags" ,params = "action=add", method = RequestMethod.POST)
    public ModelAndView addTag(@RequestParam String tagName,@RequestParam String newsId){
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
        List<Tag> tagList = newsDao.getTagsForNews(news.getNewsId());
        String tagString = "";
        for(Tag tagElem: tagList){
            tagString+= "#"+tagElem.getTagName();
        }
        news.setTagsString(tagString);
        ModelAndView mav = new ModelAndView(EDIT_NEWS);
        mav.addObject("news",news);
        return mav;
    }

    @RequestMapping(value = "/tags" ,params = "action=delete", method = RequestMethod.POST)
    public ModelAndView deleteTag(@RequestParam String tagName,@RequestParam String newsId){
        logger.debug("delete tag");
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
        news.setTagsString(tagString);
        ModelAndView mav = new ModelAndView(EDIT_NEWS);
        mav.addObject("news",news);
        return mav;
    }

    @RequestMapping(value = "/tags",method = RequestMethod.GET)
    public ModelAndView getForm(){
        logger.debug("get new form");
        List<News> newsList = newsDao.getAll();
        for (News elem: newsList){
            List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
            String tagString = "";
            for(Tag tag: tagList){
                tagString+= "#"+tag.getTagName();
            }
            elem.setTagsString(tagString);
        }

        ModelAndView mav = new ModelAndView(NEWS_LIST);
        mav.addObject("news",newsList);
        return mav;
    }
}
