package com.shestakam.news.controller;

import com.shestakam.news.dao.JdbcNewsDao;
import com.shestakam.news.dao.NewsDao;
import com.shestakam.user.dao.JdbcUserDao;
import com.shestakam.user.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by alexandr on 21.7.15.
 */
public class NewsController extends HttpServlet {

    private  final static Logger logger = LogManager.getLogger(NewsController.class);
    private NewsDao newsDao;

    public NewsController() {
        this.newsDao= new JdbcNewsDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
