package com.shestakam.tags.entity;

/**
 * Created by alexandr on 28.7.15.
 */
public class Tags {

    private Long tagId;
    private String tagName;

    public Tags() {
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
