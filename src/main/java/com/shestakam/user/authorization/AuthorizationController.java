package com.shestakam.user.authorization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthorizationController {

    private  final static Logger logger = LogManager.getLogger(AuthorizationController.class);
    private static final String LOGIN_PAGE = "login";

    @RequestMapping(value = "/")
    public String getLoginForm() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return "redirect:/news";
        }
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
