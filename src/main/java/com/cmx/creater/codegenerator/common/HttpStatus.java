package com.cmx.creater.codegenerator.common;

/**
 * @author cmx
 * @date 2019/4/1
 */
public class HttpStatus {
    public static final int UNDEFINE = 0;

    /** 2xx Success*/
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int ACCEPTED = 202;

    /** 3xx Redirection*/
    public static final int MULTIPLE_CHOICES = 300;

    /** 4xx Client errors*/
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;


    /** 5xx Server errors*/
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;
    public static final int BAD_GATEWAY = 502;
}
