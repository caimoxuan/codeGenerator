package com.cmx.creater.codegenerator.template;

import com.cmx.creater.codegenerator.common.Table;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author cmx
 * @date 2019/4/2
 */
public interface CodeCreater {

    /**
     * create all file for all tables
     * @param tables all database tables
     */
    Map<String, ByteArrayOutputStream> createCode(List<Table> tables);

    /**
     * create one file by file name
     * @param tables all database tables
     * @param tableName which one to create
     * @return file code
     */
    String createCodeWithTableName(List<Table> tables, String tableName);
}
