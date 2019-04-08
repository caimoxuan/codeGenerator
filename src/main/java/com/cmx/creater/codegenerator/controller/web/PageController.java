package com.cmx.creater.codegenerator.controller.web;

import com.cmx.creater.codegenerator.cache.SessionTableStore;
import com.cmx.creater.codegenerator.common.Table;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author cmx
 * @date 2019/4/4
 */
@Slf4j
@Controller
@RequestMapping("/pages")
public class PageController {

    @Autowired
    private SessionTableStore sessionTableStore;

    @RequestMapping(value="/index")
    public String indexPage(){
        return "index";
    }


    @RequestMapping("/tableDetail")
    public String tableDetail(@RequestParam String tableName){
        log.info("get tableDetail request : {}", tableName);

        List<Table> cacheTables = sessionTableStore.getCacheTables();

        return "pages/table";
    }

}
