package com.cmx.creater.codegenerator.common;

import lombok.Data;

/**
 * @author cmx
 * @date 2019/4/1
 */
@Data
public class GeneratorConfig {

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
    private boolean lombokEnable;


    public GeneratorConfig(){
        this.basePackageName = "com.code.generator";
        this.resourcePath = "resource";
        this.daoPath = basePackageName + ".mapper";
        this.daoSuffix = "Mapper";
        this.mapperPath = resourcePath + ".mapper";
        this.domainPath = basePackageName + ".domain";
        this.servicePath = basePackageName + ".service";
        this.basePath = basePackageName + "base";
        this.lombokEnable = true;
    }

}