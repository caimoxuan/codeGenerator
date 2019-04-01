package com.cmx.creater.codegenerator.common;

/**
 * @author cmx
 * @date 2019/4/1
 */
public class GeneratorConfig {

    private String basePackageName;

    private String domainPath;

    private String mapperPath;

    private String servicePath;

    private String resourcePath;

    private String daoPath;

    private String daoSuffix;


    public GeneratorConfig(){
        this.basePackageName = "com.code.generator";
        this.resourcePath = "resource";
        this.daoPath = basePackageName + ".mapper";
        this.daoSuffix = "Mapper";
        this.mapperPath = resourcePath + ".mapper";
        this.domainPath = basePackageName + ".domain";
        this.servicePath = basePackageName + ".service";
    }

}
