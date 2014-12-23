package com.newegg.autopricing.angular.web.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.newegg.autopricing.angular.web.security.User;
import com.newegg.autopricing.angular.web.springmvc.UserPrincipal;

@Controller
@RequestMapping(value="/angular")
public class AngularDemoController {
    private static final Logger L = LoggerFactory.getLogger(AngularDemoController.class);
    private static final String PATH = "demo";
    private static final String VIEW = PATH + "/viewByDemo";

    @RequestMapping(value="/demo", method = {RequestMethod.GET})
    public String demoView(Model model, @UserPrincipal User user){
        L.info("TEST");
        return VIEW;
    }
}
