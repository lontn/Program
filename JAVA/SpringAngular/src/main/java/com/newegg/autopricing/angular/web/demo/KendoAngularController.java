package com.newegg.autopricing.angular.web.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/Kendo")
public class KendoAngularController {
    private static final Logger L = LoggerFactory.getLogger(KendoAngularController.class);
    private static final String PATH = "kendo";
    private static final String VIEW = PATH + "/viewByKendo";
    
    @RequestMapping(value="/demo", method = {RequestMethod.GET})
    public String demoView(Model model){
        L.info("TEST");
        return VIEW;
    }
}
