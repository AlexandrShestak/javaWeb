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

}
