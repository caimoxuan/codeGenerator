package com.cmx.creater.codegenerator.common;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author cmx
 * @date 2019/4/1
 */
@Data
public class Table {

    private String tableName;

    private List<Column> primaryKeys;

    private Map<String, Column> columns;

    public Table(String tableName){
        this.tableName = tableName;
    }

}
