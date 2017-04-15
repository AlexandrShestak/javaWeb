package com.shestakam.user.controller;

import com.shestakam.user.dao.UserDao;
import com.shestakam.user.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alexandr on 12.8.15.
 */
@Controller
public class SpringUserController {
    private static final String USERS_LIST = "user/list";
    private static final String ADD_USER_PAGE = "user/add";
    private static final String EDIT_USER_PAGE = "user/edit";

    private  final static Logger logger = LogManager.getLogger(SpringUserController.class);

    @Autowired
    @Qualifier("hibernateUserDao")
    private UserDao userDao;

    @RequestMapping(value = "user" ,params = "action=add",method = RequestMethod.GET)
    public String getAddUserForm(){
        logger.debug("get user form");
        return ADD_USER_PAGE;
    }

    @RequestMapping(value = "user" ,params = "action=edit", method = RequestMethod.GET)
    public ModelAndView getEditUserForm(HttpServletRequest request){
        logger.debug("return edit user form");
        String username = request.getParameter("username");
        ModelAndView mav = new ModelAndView(EDIT_USER_PAGE);
        User user  = userDao.get(username);
        mav.addObject("user",user);
        return mav;
    }

    @RequestMapping(value = "user" , params = "action=delete", method = RequestMethod.GET)
    public ModelAndView deleteUser(HttpServletRequest request){
        logger.debug("delete user");
        String username = request.getParameter("username");
        userDao.delete(username);
        ModelAndView mav = new ModelAndView(USERS_LIST);
        mav.addObject("users", userDao.getAll());
        return  mav;
    }

    @RequestMapping(value = "user" , method = RequestMethod.GET)
    public ModelAndView getUsers(){
        ModelAndView mav = new ModelAndView(USERS_LIST);
        mav.addObject("users", userDao.getAll());
        return  mav;
    }

    @RequestMapping(value = "user" ,params = "action=add", method = RequestMethod.POST)
    public ModelAndView addUser(@ModelAttribute User user){
        logger.debug("add user");
        userDao.save(user);
        ModelAndView mav = new ModelAndView(USERS_LIST);
        mav.addObject("users", userDao.getAll());
        return  mav;
    }
    @RequestMapping(value = "user" ,params = "action=edit", method = RequestMethod.POST)
    public ModelAndView editUser(@ModelAttribute User user){
        logger.debug("edit user");
        userDao.update(user);
        ModelAndView mav = new ModelAndView(USERS_LIST);
        mav.addObject("users", userDao.getAll());
        return  mav;
    }

}
