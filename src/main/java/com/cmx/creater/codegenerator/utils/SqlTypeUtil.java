package com.cmx.creater.codegenerator.utils;

/**
 * @author cmx
 * 将数据库类型转换成java类型
 */
public final class SqlTypeUtil {

    public static String getJavaType(String jdbcType){
        switch(jdbcType){
            case "varchar":
            case "VARCHAR":
            case "text":
            case "TEXT":
            case "char":
            case "CHAR":
                return "String";
            case "int":
            case "INT":
            case "tinyint":
            case "TINYINT":
                return "Integer";
            case "bigint":
            case "BIGINT":
            case "BIGINT UNSIGNED":
            case "bigint unsigned":
            case "id":
            case "ID":
            case "timestamp":
            case "TIMESTAMP":
                return "Long";
            case "float":
            case "FLOAT":
                return "Float";
            case "double":
            case "DOUBLE":
                return "Double";
            case "decimal":
            case "DECIMAL":
                return "BigDecimal";
            case "datetime":
            case "DATETIME":
                return "Date";
            case "bit":
            case "BIT":
                return "Boolean";
            default:
                return "String";
        }
    }
}
