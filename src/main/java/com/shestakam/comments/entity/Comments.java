package com.shestakam.comments.entity;

import java.sql.Timestamp;

/**
 * Created by alexandr on 24.7.15.
 */
public class Comments {

    private Long commentId;
    private String commentText;
    private Timestamp creationDate;
    private String commentatorUsername;
    private Long newsId;

    public Comments() {
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getCommentatorUsername() {
        return commentatorUsername;
    }

    public void setCommentatorUsername(String commentatorUsername) {
        this.commentatorUsername = commentatorUsername;
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }
}
