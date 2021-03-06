package com.shestakam.user.authorization;

import com.shestakam.user.dao.UserDao;
import com.shestakam.user.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by alexandr on 12.8.15.
 */
@Controller
public class RegistrationController {

    private  final static Logger logger = LogManager.getLogger(RegistrationController.class);
    private static final String REGISTRATION_PAGE= "authorization/registration";
    private static final String START_PAGE = "/login";

    @Autowired
    @Qualifier("hibernateUserDao")
    private UserDao userDao;

    @RequestMapping(value = "/registration",method = RequestMethod.GET)
    public String getFrom(){
        logger.debug("get registration page");
        return REGISTRATION_PAGE;
    }

    @RequestMapping(value = "/registration",method = RequestMethod.POST)
    public ModelAndView registration(@ModelAttribute User user){
        logger.debug("user registration");
        String login = user.getUsername();
        if(userDao.get(login) != null){
            ModelAndView mav = new ModelAndView(REGISTRATION_PAGE);
            mav.addObject("errorMessage","Пользователь с таким именем уже существует");
            return mav;
        } else {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userDao.save(user);
            userDao.addRole(login,"ROLE_USER");
            ModelAndView mav = new ModelAndView(START_PAGE);
            return mav;
        }
    }
}
