package com.shestakam.news.entity;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by alexandr on 20.7.15.
 */
public class News {

    private Long newsId;
    private String newsText;
    private Timestamp creationDate;
    private String creatorUsername;

    public News() {
    }

    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }
}