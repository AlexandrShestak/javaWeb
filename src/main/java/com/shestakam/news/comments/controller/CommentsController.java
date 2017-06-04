/**
 * Created by alexandr on 24.7.15.
 */
package com.shestakam.news.comments.controller;

import com.shestakam.news.comments.dao.CommentsDao;
import com.shestakam.news.comments.entity.Comments;
import com.shestakam.news.dao.NewsDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

@Deprecated
public class CommentsController extends HttpServlet {

    private  final static Logger logger = LogManager.getLogger(CommentsController.class);

    private static final String NEWS_WITH_COMMENTS = "/WEB-INF/pages/news/news.jsp";

    private CommentsDao commentsDao;
    private NewsDao newsDao;

    public void setCommentsDao(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        commentsDao = (CommentsDao) ac.getBean("hibernateCommentsDao");
        newsDao = (NewsDao) ac.getBean("hibernateNewsDao");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addComment(request,response);
        }else if("delete".equals(action)){
            deleteComment(request,response);
        }else if("edit".equals(action)){
            editComment(request,response);
        }else if ("getForm".equals(action)){
            getNewsWithCommentsForm(request, response);
        }
    }

    private void editComment(HttpServletRequest request, HttpServletResponse response) {
        String commentId = request.getParameter("commentId");
        String commentText = request.getParameter("commentText");
        Comments comment = commentsDao.get(commentId);
        comment.setCommentText(commentText);
        commentsDao.update(comment);
    }

    private void deleteComment(HttpServletRequest request, HttpServletResponse response) {
        String commentId = request.getParameter("commentId");
        commentsDao.delete(commentId);
    }

    private void addComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String commentText = request.getParameter("commentText");
        String newsId = request.getParameter("newsId");
        Comments comment = new Comments();
        comment.setCommentText(commentText);
        comment.setCreationDate(new Timestamp(System.currentTimeMillis()));
        comment.setNewsId(Long.valueOf(newsId));
        comment.setCommentatorUsername((String) request.getSession().getAttribute("login"));
        String key = commentsDao.save(comment);

        JSONObject obj = new JSONObject();
        obj.put("creationDate",comment.getCreationDate().toString());
        obj.put("commentatorUsername",comment.getCommentatorUsername());
        obj.put("commentId",key);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(obj.toJSONString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("getForm".equals(action)){
            getNewsWithCommentsForm(request,response);
        }
    }

    private void getNewsWithCommentsForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("get news with comments form");
        RequestDispatcher view = request.getRequestDispatcher(NEWS_WITH_COMMENTS);
        String newsId = request.getParameter("newsId");
        request.setAttribute("news",newsDao.get(newsId));
        request.setAttribute("newsTags",newsDao.getTagsForNews(Long.valueOf(newsId)));
        request.setAttribute("comments",commentsDao.getCommentsForNews(Long.valueOf(newsId)));
        view.forward(request, response);
    }
}
