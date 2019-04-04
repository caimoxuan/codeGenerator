package com.cmx.creater.codegenerator.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cmx
 * @date 2019/4/4
 */
@Controller
@RequestMapping("/pages")
public class PageController {

    @RequestMapping(value="/index")
    public String indexPage(){
        return "index";
    }

}
