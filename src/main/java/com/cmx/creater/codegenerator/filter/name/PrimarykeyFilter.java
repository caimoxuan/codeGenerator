package com.cmx.creater.codegenerator.filter.name;

import com.cmx.creater.codegenerator.common.Column;
import com.cmx.creater.codegenerator.common.Table;

import java.util.List;

/**
 * @author cmx
 * @date 2019/4/2
 */
public class PrimarykeyFilter implements NameFilter {


    @Override
    public boolean isGenerate(Table table) {

        List<Column> primaryKeys = table.getPrimaryKeys();
        if(primaryKeys == null || primaryKeys.size() < 1){
            return false;
        }

        return true;
    }

}
