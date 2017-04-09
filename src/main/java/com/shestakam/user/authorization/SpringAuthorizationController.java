package com.shestakam.user.authorization;

import com.shestakam.news.dao.NewsDao;
import com.shestakam.user.dao.UserDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringAuthorizationController {

    private  final static Logger logger = LogManager.getLogger(SpringAuthorizationController.class);
    private static final String LOGIN_PAGE = "login";

    private UserDao userDao;
    private NewsDao newsDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    @RequestMapping(value = "/")
    public String getLoginForm(){
        return LOGIN_PAGE;
    }


    @RequestMapping(value = "/login" , method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {

        logger.debug("login");
        ModelAndView model = new ModelAndView();
        if (error != null) {
            logger.debug("Invalid username and password!");
            model.addObject("errorMessage", "Invalid username and password!");
        }
        if (logout != null) {
            logger.debug("You've been logged out successfully.");
            model.addObject("logoutMessage", "You've been logged out successfully.");
        }
        model.setViewName(LOGIN_PAGE);
        logger.debug("successfully login");
        return model;
    }
}
