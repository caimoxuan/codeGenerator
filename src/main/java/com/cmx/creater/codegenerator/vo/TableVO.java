package com.cmx.creater.codegenerator.vo;

import com.cmx.creater.codegenerator.common.Column;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author cmx
 * @Date 2019/4/1 22:25
 */

@Data
public class TableVO implements Serializable{

    private String tableName;

    private List<Column> primaryKeys;

    private Map<String, Column> columns;

}
