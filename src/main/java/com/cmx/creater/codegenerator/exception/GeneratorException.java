package com.cmx.creater.codegenerator.exception;

import lombok.Getter;

/**
 * @author cmx
 * @date 2019/4/1
 */
public class GeneratorException extends RuntimeException implements BaseException {



    protected String code;
    @Getter
    protected String message;
    @Getter
    protected int status;

    public GeneratorException(){
        super();
    }

    public GeneratorException(String code,String message, int status){
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public GeneratorException(String code,String message, int status,Throwable t){
        super(t);
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private BaseException errorInfo;

    public GeneratorException(BaseException error) {
        this.errorInfo = error;
        this.code = error.getCode();
        this.message = error.getMessage();
        this.status = error.getStatus();
    }

    public GeneratorException(BaseException error, Throwable t) {
        super(t);
        this.errorInfo = error;
        this.code = error.getCode();
        this.message = error.getMessage();
        this.status = error.getStatus();
    }

    @Override
    public String getCode(){
        return errorInfo.getCode();
    }

    @Override
    public String getMessage(){
        if(null != errorInfo){
            return errorInfo.getMessage();
        }else {
            return super.getMessage() == null ? this.message : super.getMessage();
        }
    }
}
