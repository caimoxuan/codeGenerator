package com.cmx.creater.codegenerator.domain;

import lombok.Data;

/**
 * @author cmx
 * @date 2019/4/1
 */

@Data
public class ConnectionModel {

    private String host;

    private Integer port;

    private String database;

    private String userName;

    private String password;

    private final String driver;


    public ConnectionModel(){
        this.driver = "com.mysql.cj.jdbc.Driver";
    }

}
