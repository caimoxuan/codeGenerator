package com.cmx.creater.codegenerator.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author cmx
 * @date 2019/4/1
 */

@Data
@AllArgsConstructor
public class Column {

    /**
     * 字段名称
     */
    private String columnName;

    /**
     * 字段类型
     */
    private String columnType;

    /**
     * 字段长度
     */
    private Integer dateSize;

    /**
     * 是否 not null
     */
    private Integer nullAble;

    /**
     * 备注
     */
    private String remarks;

}
