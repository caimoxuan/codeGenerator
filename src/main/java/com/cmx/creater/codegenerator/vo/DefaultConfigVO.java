package com.cmx.creater.codegenerator.vo;

import lombok.Data;

/**
 * @author cmx
 * @date 2019/4/3
 */
@Data
public class DefaultConfigVO {

    private String basePackageName;

    private String domainPath;

    private String mapperPath;

    private String servicePath;

    private String resourcePath;

    private String basePath;

    /**
     * basePackage + daoPath
     */
    private String daoPath;

    /**
     * UserDao or UserMapper
     */
    private String daoSuffix;
    /**
     * use lombok
     */
    private Boolean lombokEnable;

    public DefaultConfigVO () {
        this.basePackageName = "com.code.generator";
        this.resourcePath = "resource";
        this.daoPath = basePackageName + ".mapper";
        this.daoSuffix = "Mapper";
        this.mapperPath = resourcePath + ".mapper";
        this.domainPath = basePackageName + ".domain";
        this.servicePath = basePackageName + ".service";
        this.basePath = basePackageName + ".base";
        this.lombokEnable = true;
    }

}
