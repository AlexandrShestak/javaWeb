package com.shestakam.news.tags.dao;

import com.shestakam.db.GenericDao;
import com.shestakam.news.tags.entity.Tag;

/**
 * Created by alexandr on 28.7.15.
 */
public interface TagDao extends GenericDao<Tag> {
    Long getTagIdByName(String tagName);
    void addTagToNews(Long newsId, Long tagId);
    void deleteTagFromNews(Long newsId, Long tagId);

}
