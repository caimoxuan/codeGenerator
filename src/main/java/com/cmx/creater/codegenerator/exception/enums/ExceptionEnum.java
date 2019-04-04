package com.cmx.creater.codegenerator.exception.enums;

import com.cmx.creater.codegenerator.common.HttpStatus;
import com.cmx.creater.codegenerator.exception.BaseException;

/**
 * @author cmx
 * @date 2019/4/1
 */
public enum ExceptionEnum implements BaseException{

    /**系统异常100 */
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "10000", "系统异常"),


    /** 数据库连接 */
    SQL_SONNECTION_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "20000", "数据库连接出错"),
    CONNECTION_PARAM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "20002", "数据库连接信息异常"),
    SQL(HttpStatus.INTERNAL_SERVER_ERROR, "20003", "数据库连接出错"),
    SQL_MESSAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "20010", "获取数据库信息异常"),

    /**其他异常 */
    SELECT_NULL_ERROR(HttpStatus.BAD_REQUEST, "40001", "未选择的参数");


    private int status;
    private String code;
    private String message;

    ExceptionEnum(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
