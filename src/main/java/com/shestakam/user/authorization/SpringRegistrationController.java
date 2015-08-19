package com.shestakam.user.authorization;

import com.shestakam.user.dao.UserDao;
import com.shestakam.user.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by alexandr on 12.8.15.
 */
@Controller
public class SpringRegistrationController {

    private  final static Logger logger = LogManager.getLogger(SpringRegistrationController.class);
    private static final String NEWS_LIST = "news/list";
    private static final String REGISTRATION_PAGE= "authorization/registration";
    private static final String START_PAGE = "/login";

    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/registration",method = RequestMethod.GET)
    public String getFrom(){
        logger.debug("get registration page");
        return REGISTRATION_PAGE;
    }

    @RequestMapping(value = "/registration",method = RequestMethod.POST)
    public ModelAndView registration(@ModelAttribute User user){
        logger.debug("user registration");
        String login = user.getUsername();
        if(userDao.get(login)!=null){
            ModelAndView mav = new ModelAndView(REGISTRATION_PAGE);
            mav.addObject("errorMessage","Пользователь с таким именем уже существует");
            return mav;
        }else {
            userDao.save(user);
            userDao.addRole(login,"ROLE_USER");
            ModelAndView mav = new ModelAndView(START_PAGE);
            return mav;
        }
    }
}
