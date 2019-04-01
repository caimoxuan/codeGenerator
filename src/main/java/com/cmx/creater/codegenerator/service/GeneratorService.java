package com.cmx.creater.codegenerator.service;

import com.cmx.creater.codegenerator.common.Table;
import com.cmx.creater.codegenerator.domain.ConnectionModel;
import com.cmx.creater.codegenerator.exception.GeneratorException;
import com.cmx.creater.codegenerator.exception.enums.ExceptionEnum;
import com.cmx.creater.codegenerator.manager.GenerateManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author cmx
 * @date 2019/4/1
 */
@Slf4j
@Service
public class GeneratorService {

    @Autowired
    private GenerateManager generateManager;

    public String generateOneFile(){
        return null;
    }

    public List<Table> getTables(ConnectionModel connectionModel, Connection connection){
        return generateManager.getTables(connectionModel, connection);
    }

}
