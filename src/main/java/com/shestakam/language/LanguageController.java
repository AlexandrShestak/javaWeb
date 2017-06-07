package com.shestakam.language;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LanguageController {

    @RequestMapping(value = "/language")
    public String changeLanguage(HttpServletRequest request){
        String language = request.getParameter("language");
        String referer = request.getHeader("referer");
        if (referer.contains("language=ru")){
            referer = referer.replaceAll("ru$", language);
            return "redirect:"+ referer;
        } else if (referer.contains("language=en")) {
            referer = referer.replaceAll("en$", language);
            return "redirect:"+ referer;
        }
        if (referer.contains("?")) {
            referer+="&language="+language;
        } else {
            referer+="?language="+language;
        }
        return "redirect:"+ referer;
    }

}
