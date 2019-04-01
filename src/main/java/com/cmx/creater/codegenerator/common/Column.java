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

    private String columnName;

    private String columnType;

    private Integer dateSize;

    private Integer nullAble;

    private String remarks;

}
