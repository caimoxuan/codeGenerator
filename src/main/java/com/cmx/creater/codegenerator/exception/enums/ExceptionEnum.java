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
    SQL_SONNECTION_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "20000", "数据库连接出错");

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
