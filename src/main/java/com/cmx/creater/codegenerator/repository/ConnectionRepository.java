package com.cmx.creater.codegenerator.repository;

import com.cmx.creater.codegenerator.common.HttpStatus;
import com.cmx.creater.codegenerator.domain.ConnectionModel;
import com.cmx.creater.codegenerator.exception.GeneratorException;
import com.cmx.creater.codegenerator.exception.enums.ExceptionEnum;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author cmx
 * @date 2019/4/1
 */
@Repository
public class ConnectionRepository {


    public Connection getConnection(ConnectionModel cModel) throws GeneratorException {

        //将驱动加载到内存中
        try {
            Class.forName(cModel.getDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://" + cModel.getHost() + "/" + cModel.getDatabase();

        try{
            Connection connection = DriverManager.getConnection(url, cModel.getUserName(), cModel.getPassword());
            return connection;
        }catch (Exception e){
            throw new GeneratorException(ExceptionEnum.SQL_SONNECTION_FAIL.getCode(), "connect to "
                    + url + " use" + cModel.getUserName() + "error : "
                    + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
