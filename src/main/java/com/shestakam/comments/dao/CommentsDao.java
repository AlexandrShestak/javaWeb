package com.shestakam.comments.dao;

import com.shestakam.comments.entity.Comments;
import com.shestakam.helpers.GenericDao;

import java.util.List;

/**
 * Created by alexandr on 24.7.15.
 */
public interface CommentsDao extends GenericDao<Comments> {
    List<Comments> getCommentsForNews(Long newsId);
}
