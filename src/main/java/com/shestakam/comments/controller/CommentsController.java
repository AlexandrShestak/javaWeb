/**
 * Created by alexandr on 24.7.15.
 */
package com.shestakam.comments.controller;

import com.shestakam.comments.dao.CommentsDao;
import com.shestakam.comments.dao.JdbcCommentsDao;
import com.shestakam.comments.entity.Comments;
import com.shestakam.news.dao.JdbcNewsDao;
import com.shestakam.news.dao.NewsDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;


public class CommentsController extends HttpServlet {

    private  final static Logger logger = LogManager.getLogger(CommentsController.class);
    private static final String NEWS_WITH_COMMENTS = "/pages/newsWithComments/newsWithComments.jsp";

    private CommentsDao commentsDao;
    private NewsDao newsDao;

    public CommentsController() {
        this.commentsDao = new JdbcCommentsDao();
        this.newsDao = new JdbcNewsDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            String commentText = request.getParameter("commentText");
            String newsId = request.getParameter("newsId");
            Comments comment = new Comments();
            comment.setCommentText(commentText);
            comment.setCreationDate(new Timestamp(System.currentTimeMillis()));
            comment.setNewsId(Long.valueOf(newsId));
            comment.setCommentatorUsername((String) request.getSession().getAttribute("login"));
            String key = commentsDao.add(comment);

            JSONObject obj = new JSONObject();
            obj.put("creationDate",comment.getCreationDate().toString());
            obj.put("commentatorUsername",comment.getCommentatorUsername());
            obj.put("commentId",key);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(obj.toJSONString());
        }else if("delete".equals(action)){
            String commentId = request.getParameter("commentId");
            commentsDao.delete(commentId);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("getForm".equals(action)){
            logger.debug("get news with comments form");
            RequestDispatcher view = request.getRequestDispatcher(NEWS_WITH_COMMENTS);
            String newsId = request.getParameter("newsId");
            request.setAttribute("news",newsDao.get(newsId));
            request.setAttribute("comments",commentsDao.getCommentsForNews(Long.valueOf(newsId)));
            view.forward(request, response);
        }
    }
}
