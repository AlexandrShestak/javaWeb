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

/**
 * Created by alexandr on 11.8.15.
 */
@Controller
public class SpringAuthorizationController {

    private  final static Logger logger = LogManager.getLogger(SpringAuthorizationController.class);
    private static final String START_PAGE = "index";
    private static final String LOGIN_PAGE = "login";
    private static final String NEWS_LIST = "news/list";

    private UserDao userDao;
    private NewsDao newsDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

   /* @RequestMapping(value = "/logout")
    public String logout(HttpSession session) {
        logger.debug("logout");
        session.removeAttribute("login");
        session.invalidate();
        return START_PAGE;
    }*/
    @RequestMapping(value = "/")
    public String getLoginForm(){
        return LOGIN_PAGE;
    }

  /*  @RequestMapping(value = "/login" , method = RequestMethod.GET)
    public ModelAndView getNewsIfLogin(HttpSession session){
        logger.debug("get News If Login");
        String login = (String) session.getAttribute("login");
        if( login == null){
            ModelAndView mav = new ModelAndView();
            mav.setViewName(LOGIN_PAGE);
            return mav;
        }else {
            List<News> newsList = newsDao.getAll();
            for (News elem: newsList){
                List<Tag> tagList = newsDao.getTagsForNews(elem.getNewsId());
                String tagString = new String();
                for(Tag tag: tagList){
                    tagString+= "#"+tag.getTagName();
                }
                elem.setTagsString(tagString);
            }
            ModelAndView mav = new ModelAndView();
            mav.setViewName(NEWS_LIST);
            mav.addObject("news",newsList);
            return mav;
        }
    }*/

    @RequestMapping(value = "/login" , method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("errorMessage", "Invalid username and password!");
        }
        if (logout != null) {
            model.addObject("errorMessage", "You've been logged out successfully.");
        }
        model.setViewName(LOGIN_PAGE);

        return model;
    }
}