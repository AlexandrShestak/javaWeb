package com.shestakam.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReactController {

    @RequestMapping(value = "/react", method = RequestMethod.GET)
    String render() {
        return "react";
    }

    @RequestMapping(value = "/reactStart", method = RequestMethod.GET)
    @ResponseBody
    String start() {
        return "tratata";
    }
}
