package com.shestakam.news.comments.dao;

import com.shestakam.news.comments.entity.Comments;
import com.shestakam.db.GenericDao;

import java.util.List;

/**
 * Created by alexandr on 24.7.15.
 */
public interface CommentsDao extends GenericDao<Comments> {
    List<Comments> getCommentsForNews(Long newsId);
}
