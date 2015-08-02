package com.shestakam.news.tags.dao;

import com.shestakam.db.GenericDao;
import com.shestakam.news.tags.entity.Tags;

/**
 * Created by alexandr on 28.7.15.
 */
public interface TagDao extends GenericDao<Tags> {
    Long getTagIdByName(String tagName);
    void addTagToNews(Long newsId, Long tagId);

}