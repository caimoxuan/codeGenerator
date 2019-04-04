package com.cmx.creater.codegenerator.common;

import com.cmx.creater.codegenerator.exception.BaseException;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cmx
 * @param <T> result data
 */
@Data
public class ApiResult<T> implements Serializable {

    private String code;

    private String message;

    private T data;
    /**
     * httpStatus
     */
    private Integer status;

    public ApiResult<T> successResult(T data){
        this.code = "200";
        this.message = "success";
        this.data = data;
        this.status = HttpStatus.OK;
        return this;
    }

    public ApiResult<T> failResult(String code, String message, Integer status){
        this.code = code;
        this.message = message;
        this.status = status;
        return this;
    }

    public ApiResult<T> failResult(BaseException exp){
        this.status = exp.getStatus();
        this.message = exp.getMessage();
        this.code = exp.getCode();
        return this;
    }


}
