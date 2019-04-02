package com.cmx.creater.codegenerator.manager;

import com.cmx.creater.codegenerator.common.Table;
import com.cmx.creater.codegenerator.domain.ConnectionModel;
import com.cmx.creater.codegenerator.exception.GeneratorException;
import com.cmx.creater.codegenerator.exception.enums.ExceptionEnum;
import com.cmx.creater.codegenerator.repository.ConnectionRepository;
import com.cmx.creater.codegenerator.repository.TableInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author cmx
 * @date 2019/4/1
 */
@Slf4j
@Component
public class GenerateManager {


    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private TableInfoRepository tableInfoRepository;


    public List<Table> getTables(ConnectionModel connectionModel, Connection connection){

        if(connection != null){
            try {
                return tableInfoRepository.getTableName(connection);
            } catch (SQLException e) {
                log.error("get table message error : {}", e);
                throw new GeneratorException(ExceptionEnum.SQL_MESSAGE_ERROR);
            }
        }

        if(connectionModel == null){
            throw new GeneratorException(ExceptionEnum.CONNECTION_PARAM_ERROR);
        }

        Connection reConnection = connectionRepository.getConnection(connectionModel);
        if(reConnection == null){
            throw new GeneratorException(ExceptionEnum.SQL_SONNECTION_FAIL);
        }

        return getTables(null, reConnection);
    }


}
