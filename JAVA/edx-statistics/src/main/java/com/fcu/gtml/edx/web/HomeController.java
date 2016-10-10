package com.fcu.gtml.edx.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/home")
public class HomeController {
    private static final Logger L = LogManager.getLogger();

    /**
     * 進入首頁
     * @param model
     * @return
     */
    @RequestMapping(method = {RequestMethod.GET})
    public String View(Model model){
        return "home";
    }
}
