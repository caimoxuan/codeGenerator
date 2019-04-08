package com.cmx.creater.codegenerator.controller;

import com.cmx.creater.codegenerator.cache.SessionTableStore;
import com.cmx.creater.codegenerator.common.ApiResult;
import com.cmx.creater.codegenerator.common.GeneratorConfig;
import com.cmx.creater.codegenerator.enums.CodeCreateType;
import com.cmx.creater.codegenerator.vo.DefaultConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cmx
 * @date 2019/4/3
 */
@Slf4j
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private SessionTableStore sessionTableStore;


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


    @RequestMapping(value = "/conf", method = RequestMethod.GET)
    public ApiResult<DefaultConfigVO> defaultConfig(){

        ApiResult<DefaultConfigVO> result = new ApiResult<>();

        DefaultConfigVO defaultConfigVO = new DefaultConfigVO();

        GeneratorConfig config = new GeneratorConfig();

        if(sessionTableStore.getCacheConfig() != null){
            GeneratorConfig cacheConfig = sessionTableStore.getCacheConfig();
            BeanUtils.copyProperties(cacheConfig, defaultConfigVO);
            return result.successResult(defaultConfigVO);
        }

        BeanUtils.copyProperties(config, defaultConfigVO);

        return result.successResult(defaultConfigVO);
    }

    @RequestMapping(value = "/cache", method = RequestMethod.POST)
    public ApiResult<GeneratorConfig> cacheConfig(@RequestBody DefaultConfigVO config){
        log.info("config get cache request : {}", config);

        ApiResult<GeneratorConfig> result = new ApiResult<>();

        GeneratorConfig generatorConfig = new GeneratorConfig();

        BeanUtils.copyProperties(config, generatorConfig);

        sessionTableStore.setCacheConfig(generatorConfig);

        return result.successResult(sessionTableStore.getCacheConfig());
    }


}
