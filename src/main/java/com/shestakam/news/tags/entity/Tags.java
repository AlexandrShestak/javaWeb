package com.shestakam.news.tags.entity;

import com.shestakam.news.entity.News;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by alexandr on 28.7.15.
 */
@Entity
@Table(name="tags")
public class Tags {

    private Long tagId;
    private String tagName;
    private Set<News> newsSet = new HashSet<>(0);

    public Tags() {
    }

    @Id
    @GeneratedValue
    @Column(name="tag_id",unique = true,nullable = false)
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    @Column(name="tag_name",length = 20)
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @ManyToMany(mappedBy="tagsSet")
    @OrderBy("creationDate")
    public Set<News> getNewsSet() {
        return newsSet;
    }

    public void setNewsSet(Set<News> newsSet) {
        this.newsSet = newsSet;
    }
}
