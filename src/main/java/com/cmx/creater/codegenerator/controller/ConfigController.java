package com.cmx.creater.codegenerator.controller;

import com.cmx.creater.codegenerator.common.ApiResult;
import com.cmx.creater.codegenerator.common.GeneratorConfig;
import com.cmx.creater.codegenerator.enums.CodeCreateType;
import com.cmx.creater.codegenerator.vo.DefaultConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cmx
 * @date 2019/4/3
 */
@RestController
@RequestMapping("/config")
public class ConfigController {


    @RequestMapping(value = "/createType", method = RequestMethod.GET)
    public ApiResult<List<String>> createType(){

        ApiResult<List<String>> result = new ApiResult<>();

        List<String> createType = new ArrayList<>();

        CodeCreateType[] values = CodeCreateType.values();
        for(CodeCreateType type : values){
            createType.add(type.getKey());
        }

        return result.successResult(createType);

    }


    @RequestMapping(value = "/defaultConfig", method = RequestMethod.GET)
    public ApiResult<DefaultConfigVO> defaultConfig(){

        ApiResult<DefaultConfigVO> result = new ApiResult<>();

        GeneratorConfig config = new GeneratorConfig();

        DefaultConfigVO defaultConfigVO = new DefaultConfigVO();

        BeanUtils.copyProperties(config, defaultConfigVO);

        return result.successResult(defaultConfigVO);

    }


}
