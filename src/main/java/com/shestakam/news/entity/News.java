package com.shestakam.news.entity;

import com.shestakam.news.comments.entity.Comments;
import com.shestakam.news.tags.entity.Tags;
import com.shestakam.user.entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alexandr on 20.7.15.
 */
@Entity
@Table(name = "news")
public class News {

    private Long newsId;
    private String newsText;
    private Timestamp creationDate;
    private String creatorUsername;
    private String tagsString;
    private User creator;
    private Set<Tags> tagsSet = new HashSet<>(0);
    private Set<Comments> commentsSet = new HashSet<>(0);

    public News() {
        tagsString = new String();
    }

    public String getTagsString() {
        return tagsString;
    }

    public void setTagsString(String tagsString) {
        this.tagsString = tagsString;
    }

    @Id
    @Column(name = "news_id" ,unique = true, nullable = false)
    @GeneratedValue
    public Long getNewsId() {
        return newsId;
    }

    public void setNewsId(Long newsId) {
        this.newsId = newsId;
    }

    @Column(name="news_text",nullable = false,length = 2000)
    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    @Column(name = "creation_date",nullable = false)
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


    @ManyToOne()
    @JoinColumn(name ="creator_username")
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @ManyToMany
    @JoinTable(name="news_tags",
            joinColumns={@JoinColumn(name="news_is")},
            inverseJoinColumns={@JoinColumn(name="tag_id")})
    public Set<Tags> getTagsSet() {
        return tagsSet;
    }

    public void setTagsSet(Set<Tags> tagsSet) {
        this.tagsSet = tagsSet;
    }

    @OneToMany(mappedBy ="myNews")
    public Set<Comments> getCommentsSet() {
        return commentsSet;
    }

    public void setCommentsSet(Set<Comments> commentsSet) {
        this.commentsSet = commentsSet;
    }
}
