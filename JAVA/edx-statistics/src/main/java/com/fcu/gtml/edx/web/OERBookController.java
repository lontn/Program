package com.fcu.gtml.edx.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/oerbook")
public class OERBookController {
    private static final Logger L = LogManager.getLogger();

    @RequestMapping(method = {RequestMethod.GET})
    public String View(Model model){
        return "oerbook";
    }
}
