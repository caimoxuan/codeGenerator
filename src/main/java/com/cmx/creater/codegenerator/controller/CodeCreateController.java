package com.cmx.creater.codegenerator.controller;

import com.cmx.creater.codegenerator.cache.SessionTableStore;
import com.cmx.creater.codegenerator.common.ApiResult;
import com.cmx.creater.codegenerator.exception.GeneratorException;
import com.cmx.creater.codegenerator.service.GeneratorService;
import com.cmx.creater.codegenerator.template.BeanCreater;
import com.cmx.creater.codegenerator.template.DaoCreater;
import com.cmx.creater.codegenerator.template.MapperCreater;
import com.cmx.creater.codegenerator.utils.HtmlCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cmx
 * @date 2019/4/2
 */
@Slf4j
@RestController
@RequestMapping("/code")
public class CodeCreateController {

    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private SessionTableStore sessionTableStore;

    @RequestMapping(value = "/mapper", method = RequestMethod.GET)
    public ApiResult<String> createMapperCode(@RequestParam String tableName){
        log.info("get createMapperCode request table : {}", tableName);

        ApiResult<String> result = new ApiResult<>();
        try {
            String content = generatorService.generateOneSimpleFileContent(tableName, sessionTableStore, MapperCreater.class);
            return result.successResult(HtmlCodeUtil.parseXmlCode(content));
        }catch (GeneratorException e){
           return result.failResult(e);
        }
    }


    @RequestMapping(value = "/bean", method = RequestMethod.GET)
    public ApiResult<String> createBeanCode(@RequestParam String tableName){
        log.info("get createBeanCode request table : {}", tableName);

        ApiResult<String> result = new ApiResult<>();
        try {
            String content = generatorService.generateOneSimpleFileContent(tableName, sessionTableStore, BeanCreater.class);
            return result.successResult(HtmlCodeUtil.parseCodeToHtml(content));
        }catch (GeneratorException e){
            return result.failResult(e);
        }
    }

    @RequestMapping(value = "/dao", method = RequestMethod.GET)
    public ApiResult<String> createDaoCode(@RequestParam String tableName){
        log.info("get createDaoCode request table : {}", tableName);

        ApiResult<String> result = new ApiResult<>();
        try {
            String content = generatorService.generateOneSimpleFileContent(tableName, sessionTableStore, DaoCreater.class);
            return result.successResult(HtmlCodeUtil.parseCodeToHtml(content));
        }catch (GeneratorException e){
            return result.failResult(e);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public void getZipCode(HttpServletResponse response){
        log.info("get zipCode request");
        List<Class> classes = new ArrayList<>();
        classes.add(MapperCreater.class);
        classes.add(DaoCreater.class);
        classes.add(BeanCreater.class);
        InputStream zipCode = null;
        ServletOutputStream outputStream = null;

        response.setHeader("Content-Disposition", "attachment; filename=code.zip");
        try {
            zipCode = generatorService.getZipCode(sessionTableStore, classes);
            outputStream = response.getOutputStream();

            byte[] bytes = new byte[1024];

            while(zipCode.read(bytes) != -1){
                outputStream.write(bytes);
            }
            outputStream.flush();
        }catch (GeneratorException e){
            log.error("get zip code error : {}", e);
        }catch (Exception e){
            log.error("write stream get error : {}", e);
        }finally {
            try {
                if (zipCode != null) {
                    zipCode.close();
                }
                if(outputStream != null){
                    outputStream.close();
                }
            }catch (Exception e){}
        }
    }


}
