package com.cmx.creater.codegenerator.controller;

import com.cmx.creater.codegenerator.cache.SessionTableStore;
import com.cmx.creater.codegenerator.common.ApiResult;
import com.cmx.creater.codegenerator.enums.CodeCreateType;
import com.cmx.creater.codegenerator.exception.GeneratorException;
import com.cmx.creater.codegenerator.service.GeneratorService;
import com.cmx.creater.codegenerator.template.BeanAbstractCreater;
import com.cmx.creater.codegenerator.template.DaoAbstractCreater;
import com.cmx.creater.codegenerator.template.MapperAbstractCreater;
import com.cmx.creater.codegenerator.util.HtmlCodeUtil;
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
            String content = generatorService.generateOneSimpleFileContent(tableName, sessionTableStore, MapperAbstractCreater.class);
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
            String content = generatorService.generateOneSimpleFileContent(tableName, sessionTableStore, BeanAbstractCreater.class);
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
            String content = generatorService.generateOneSimpleFileContent(tableName, sessionTableStore, DaoAbstractCreater.class);
            return result.successResult(HtmlCodeUtil.parseCodeToHtml(content));
        }catch (GeneratorException e){
            return result.failResult(e);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public void getZipCode(HttpServletResponse response, @RequestParam(value = "types") List<String> types){
        log.info("get zipCode request, {}", types.size());
        List<Class> classes = new ArrayList<>();

        if(types.size() < 1){
            return;
        }

        for(String type : types){
            CodeCreateType codeCreateType = CodeCreateType.getType(type);
            if(codeCreateType != null){
                classes.add(codeCreateType.getClazz());
            }
        }

        InputStream zipCode = null;
        ServletOutputStream outputStream = null;

        try {
            zipCode = generatorService.getZipCode(sessionTableStore, classes);
            if(zipCode == null){
                log.warn("get zip stream fail");
                return;
            }
            response.setHeader("Content-Disposition", "attachment; filename=code.zip");
            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int index;
            while((index = zipCode.read(bytes)) != -1){
                outputStream.write(bytes, 0, index);
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
