package com.cmx.creater.codegenerator.exception;

/**
 * @author cmx
 * @date 2019/4/1
 */
public interface BaseException {

    /**
     * get exp code
     * @return
     */
    String getCode();

    /**
     * get exp message
     * @return
     */
    String getMessage();

    /**
     * get HttpStatus
     * @return
     */
    int getStatus();

}
