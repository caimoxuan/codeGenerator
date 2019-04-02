package com.cmx.creater.codegenerator.filter.name;

import com.cmx.creater.codegenerator.common.Table;

/**
 * @author cmx
 * @date 2019/4/2
 */
public class TableNameFilter implements NameFilter{

    private static String[] tableFilter = {"qrtz", "QRTZ"};

    @Override
    public boolean isGenerate(Table table){

        String tableName = table.getTableName();

        if(tableName == null){
            return false;
        }

        for(String filterName : tableFilter){
            if(tableName.indexOf(filterName) != -1){
                return false;
            }
        }

        return true;
    }
}
