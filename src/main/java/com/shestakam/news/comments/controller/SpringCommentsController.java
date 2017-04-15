package com.shestakam.news.comments.controller;

import com.shestakam.news.comments.dao.CommentsDao;
import com.shestakam.news.comments.entity.Comments;
import com.shestakam.news.dao.NewsDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;

/**
 * Created by alexandr on 15.8.15.
 */
@Controller
public class SpringCommentsController {
    private  final static Logger logger = LogManager.getLogger(SpringCommentsController.class);

    private static final String NEWS_WITH_COMMENTS = "news/news";

    @Qualifier("hibernateCommentsDao")
    @Autowired
    private CommentsDao commentsDao;

    @Qualifier("hibernateNewsDao")
    @Autowired
    private NewsDao newsDao;

    @RequestMapping(value = "/comments",params = "action=add",method = RequestMethod.POST,produces="application/json")
    @ResponseBody
    public JSONObject addComment(@RequestParam String commentText,@RequestParam String newsId){
        logger.debug("add comment");
        Comments comment = new Comments();
        comment.setCommentText(commentText);
        comment.setCreationDate(new Timestamp(System.currentTimeMillis()));
        comment.setNewsId(Long.valueOf(newsId));
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        comment.setCommentatorUsername(username);
        String key = commentsDao.save(comment);

        JSONObject obj = new JSONObject();
        obj.put("creationDate",comment.getCreationDate().toString());
        obj.put("commentatorUsername",comment.getCommentatorUsername());
        obj.put("commentId",key);
        return obj;
    }

    @RequestMapping(value = "/comments",params = "action=edit",method = RequestMethod.POST)
    @ResponseBody
    public String editComment(@RequestParam String commentText,@RequestParam String commentId){
        logger.debug("edit comment");
        Comments comment = commentsDao.get(commentId);
        comment.setCommentText(commentText);
        commentsDao.update(comment);
        return "";
    }

    @RequestMapping(value = "/comments",params = "action=delete",method = RequestMethod.POST)
    @ResponseBody
    public String deleteComment(@RequestParam String commentId){
        logger.debug("delete comment");
        commentsDao.delete(commentId);
        return "";
    }
    @RequestMapping(value = "/comments")
    public ModelAndView getNewsFormWithComments(@RequestParam String newsId){
        logger.debug("get news with comments");
        ModelAndView mav = new ModelAndView(NEWS_WITH_COMMENTS);
        mav.addObject("news",newsDao.get(newsId));
        mav.addObject("newsTags",newsDao.getTagsForNews(Long.valueOf(newsId)));
        mav.addObject("comments",commentsDao.getCommentsForNews(Long.valueOf(newsId)));
        return mav;
    }
}
