package com.cmx.creater.codegenerator.filter.name;

import com.cmx.creater.codegenerator.common.Table;

/**
 * @author cmx
 * @date 2019/4/2
 */
public interface NameFilter {

    /**
     * judge table create code or not
     * @param table
     * @return false : don't create, true : create
     */
    boolean isGenerate(Table table);

}
