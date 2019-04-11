package com.cmx.creater.codegenerator.service;

import com.cmx.creater.codegenerator.cache.SessionTableStore;
import com.cmx.creater.codegenerator.common.Table;
import com.cmx.creater.codegenerator.domain.ConnectionModel;
import com.cmx.creater.codegenerator.exception.GeneratorException;
import com.cmx.creater.codegenerator.exception.enums.ExceptionEnum;
import com.cmx.creater.codegenerator.manager.GenerateManager;
import com.cmx.creater.codegenerator.template.CodeCreater;
import com.cmx.creater.codegenerator.template.factory.CreaterFactory;
import com.cmx.creater.codegenerator.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cmx
 * @date 2019/4/1
 */
@Slf4j
@Service
public class GeneratorService {

    @Autowired
    private GenerateManager generateManager;

    public List<Table> getTables(ConnectionModel connectionModel, Connection connection){
        return generateManager.getTables(connectionModel, connection);
    }

    public String generateOneSimpleFileContent(String tableName, SessionTableStore store, Class clazz){

        if(store.getCacheTables() == null){
            throw new GeneratorException(ExceptionEnum.NOT_CONFIG_CONNECT);
        }

        if(store.getCacheTables().size() < 1){
            return null;
        }

        CodeCreater codeCreater = CreaterFactory.getCodeCreater(clazz);

        if(codeCreater == null){
            log.error("get codeCreater fail with name : {}", clazz.getName());
            throw new GeneratorException(ExceptionEnum.SYSTEM_ERROR);
        }

        codeCreater.setGeneratorConfig(store.getCacheConfig());


        return codeCreater.createCodeWithTableName(store.getCacheTables(), tableName);
    }


    public InputStream getZipCode(SessionTableStore store, List<Class> filters){

        if(store.getCacheTables() == null || store.getCacheTables().size() < 1){
            return null;
        }

        Map<String, ByteArrayOutputStream> resultMap = new HashMap<>(16);

        for(Class clazz : filters){
            CodeCreater codeCreater = CreaterFactory.getCodeCreater(clazz);
            if(codeCreater == null){
                continue;
            }
            codeCreater.setGeneratorConfig(store.getCacheConfig());
            Map<String, ByteArrayOutputStream> codeMap = codeCreater.createCode(store.getCacheTables());
            resultMap.putAll(codeMap);
        }

        try {
            return ZipUtil.zipOutputStreams(resultMap);
        } catch (IOException e) {
            log.info("zip map stream get error : {}", e);
            throw new GeneratorException(ExceptionEnum.SYSTEM_ERROR);
        }

    }

}
