package com.shestakam.language;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by alexandr on 18.8.15.
 */
@Controller
public class SpringLanguage {

    @RequestMapping(value = "/language")
    public String changeLanguage(HttpServletRequest request){
        String language = request.getParameter("language");
        String referer = request.getHeader("referer");
        if (referer.contains("language")){
            referer.replace("ru",language);
            referer.replace("en",language);
            return "redirect:"+ referer;
        }
        if (referer.contains("?")){
            referer+="&language="+language;
        }else{
            referer+="?language="+language;
        }
        return "redirect:"+ referer;
    }

}
