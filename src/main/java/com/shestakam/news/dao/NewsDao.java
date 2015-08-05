package com.shestakam.news.dao;

import com.shestakam.db.GenericDao;
import com.shestakam.news.entity.News;
import com.shestakam.news.tags.entity.Tag;

import java.util.List;

/**
 * Created by alexandr on 20.7.15.
 */
public interface NewsDao extends GenericDao<News> {

    List<Tag> getTagsForNews(Long newsId);
    List<News> searchNewsByTag(String tagName);
    List<News> searchNewsByCreator(String creator);
    List<News> searchNewsByCreatorAndTag(String creator,String tagName);

}
