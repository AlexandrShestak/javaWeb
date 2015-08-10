package com.shestakam.news.tags.dao;

import com.shestakam.db.GenericDao;
import com.shestakam.news.tags.entity.Tag;

/**
 * Created by alexandr on 28.7.15.
 */
public interface TagDao extends GenericDao<Tag> {
    Tag getTagByName(String tagName);

}
