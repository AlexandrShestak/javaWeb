package com.shestakam.news.controller;

import com.shestakam.news.dao.NewsDao;
import com.shestakam.news.entity.News;
import com.shestakam.news.search.Searcher;
import com.shestakam.news.tags.dao.TagDao;
import com.shestakam.news.tags.entity.Tag;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alexandr on 14.8.15.
 */
@Controller
public class NewsController {

    private static final String NEWS_LIST = "news/list";
    private static final String EDIT_NEWS = "news/edit";
    private static final String ADD_NEWS = "news/add";

    private  final static Logger logger = LogManager.getLogger(NewsController.class);

    @Autowired
    @Qualifier("hibernateNewsDao")
    private NewsDao newsDao;

    @Autowired
    @Qualifier("hibernateTagsDao")
    private TagDao tagDao;

    @Autowired
    private Searcher searcher;

    @RequestMapping(value = "/newsForm", method = RequestMethod.GET)
    public String getAddNewsForm() {
        logger.debug("Get add news form");
        return ADD_NEWS;
    }

    @RequestMapping(value = "/newsEdit", method = RequestMethod.GET)
    public ModelAndView getEditNewsForm(@RequestParam String newsId){
        logger.debug("Get news edit form");

        ModelAndView mav = new ModelAndView(EDIT_NEWS);

        News news = newsDao.get(newsId);
        List<Tag> tagList = newsDao.getTagsForNews(news.getNewsId());
        news.setTagsString(tagList.stream().map(Tag::getTagName).collect(Collectors.joining("#")));
        mav.addObject("news", news);

        return mav;
    }

    @RequestMapping(value = "/news", method = RequestMethod.GET)
    public ModelAndView getAllNews() {
        List<News> newsList = newsDao.getAll();
        for (News elem: newsList){
            List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
            elem.setTagsString(tagList.stream().map(Tag::getTagName).collect(Collectors.joining("#")));
        }
        ModelAndView mav = new ModelAndView(NEWS_LIST);
        mav.addObject("news",newsList);
        return mav;
    }

    @RequestMapping(value = "/newsDelete", method = RequestMethod.GET)
    public ModelAndView deleteNews(@RequestParam String newsId){
        logger.debug("Delete news with id: " + newsId);
        newsDao.delete(newsId);

        List<News> newsList = newsDao.getAll();
        for (News elem: newsList){
            List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
            elem.setTagsString(tagList.stream().map(Tag::getTagName).collect(Collectors.joining("#")));
        }
        ModelAndView mav = new ModelAndView(NEWS_LIST);
        mav.addObject("news",newsList);
        return mav;
    }

    @RequestMapping(value = "/newsSearch", method = RequestMethod.GET)
    public ModelAndView searchNews(@RequestParam(value = "tag",required = false)String tagName,
                                   @RequestParam(required = false) String username){
        if (!StringUtils.isEmpty(tagName) && StringUtils.isEmpty(username)) {
            List<News> newsList = newsDao.searchNewsByTag(tagName);
            for (News elem : newsList) {
                List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
                elem.setTagsString(tagList.stream().map(Tag::getTagName).collect(Collectors.joining("#")));
            }
            ModelAndView mav = new ModelAndView(NEWS_LIST);
            mav.addObject("news",newsList);
            return mav;
        } else if (StringUtils.isEmpty(tagName) && !StringUtils.isEmpty(username)) {
            List<News> newsList = newsDao.searchNewsByCreator(username);
            for (News elem : newsList) {
                List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
                elem.setTagsString(tagList.stream().map(Tag::getTagName).collect(Collectors.joining("#")));
            }
            ModelAndView mav = new ModelAndView(NEWS_LIST);
            mav.addObject("news",newsList);
            return mav;
        } else {
            List<News> newsList = newsDao.searchNewsByCreatorAndTag(username,tagName);
            for (News elem : newsList) {
                List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
                elem.setTagsString(tagList.stream().map(Tag::getTagName).collect(Collectors.joining("#")));
            }
            ModelAndView mav = new ModelAndView(NEWS_LIST);
            mav.addObject("news",newsList);
            return mav;
        }
    }

    @RequestMapping(value = "/news", method = RequestMethod.POST)
    public ModelAndView addNews(@RequestParam String newsText,
                                @RequestParam(value = "tags")String tagStr) throws UnsupportedEncodingException {
        logger.debug("Add news");
        Timestamp newsCreationDate = new Timestamp(System.currentTimeMillis());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        String newsCommentator = username;
        News news = new News();
        news.setCreatorUsername(newsCommentator);
        news.setCreationDate(newsCreationDate);
        news.setNewsText(newsText);
        String newsId = newsDao.save(news);
        String[] tags  = tagStr.split(";");
        for(String tagName : tags) {
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
        }
        List<News> newsList = newsDao.getAll();

        for (News elem: newsList){
            List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
            elem.setTagsString(tagList.stream().map(Tag::getTagName).collect(Collectors.joining("#")));
        }
        ModelAndView mav = new ModelAndView(NEWS_LIST);
        mav.addObject("news", newsList);
        return mav;
    }

    @RequestMapping(value = "/news" , params = "action=edit", method = RequestMethod.POST)
    public ModelAndView editNews(@RequestParam String newsText,@RequestParam String newsId) throws UnsupportedEncodingException {
        logger.debug("edit news");
        newsText = new String(newsText.getBytes("iso-8859-1"), "UTF-8");
        Timestamp newsCreationDate = new Timestamp(System.currentTimeMillis());

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        String newsCommentator = username;
        News news = new News();
        news.setNewsId(Long.valueOf(newsId));
        news.setCreatorUsername(newsCommentator);
        news.setCreationDate(newsCreationDate);
        news.setNewsText(newsText);
        newsDao.update(news);
        List<News> newsList = newsDao.getAll();

        for (News elem: newsList){
            List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
            elem.setTagsString(tagList.stream().map(Tag::getTagName).collect(Collectors.joining("#")));
        }
        ModelAndView mav = new ModelAndView(NEWS_LIST);
        mav.addObject("news", newsList);
        return mav;
    }

    @RequestMapping(value = "/fullTextSearch", method = RequestMethod.POST)
    public ModelAndView searchNews(@RequestParam(value = "query") String query) {
        List<Long> newsIds = searcher.search(query);

        ModelAndView mav = new ModelAndView(NEWS_LIST);
        if (newsIds.size() == 0) {
            mav.addObject("news", new ArrayList<>());
            return mav;
        }
        List<News> newsList = newsDao.findNewsByIds(newsIds);
        mav.addObject("news", newsList);
        return mav;
    }
}
