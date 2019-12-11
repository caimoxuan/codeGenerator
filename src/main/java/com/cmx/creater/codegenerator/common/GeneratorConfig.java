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
    private Boolean lombokEnable;


    public GeneratorConfig(){
        this.basePackageName = "com.code.generator";
        this.resourcePath = "resource";
        this.daoPath = basePackageName + ".mapper";
        this.daoSuffix = "Mapper";
        this.mapperPath = resourcePath + ".mapper";
        this.domainPath = basePackageName + ".entity";
        this.servicePath = basePackageName + ".service";
        this.basePath = basePackageName + ".base";
        this.lombokEnable = true;
    }

    public void setDaoPath(String daoPath){
        if(daoPath == null){
            return;
        }
        this.daoPath = basePackageName + "." + daoPath.substring(daoPath.lastIndexOf(".") + 1);
    }

    public void setMapperPath(String mapperPath){
        if(mapperPath == null){
            return;
        }
        this.mapperPath = resourcePath + "." + mapperPath.substring(mapperPath.lastIndexOf(".") + 1);
    }

    public void setDomainPath(String domainPath){
        if(domainPath == null){
            return;
        }
        this.domainPath = basePackageName + "." + domainPath.substring(domainPath.lastIndexOf(".") + 1);
    }

    public void setServicePath(String servicePath){
        if(servicePath == null){
            return;
        }
        this.servicePath = basePackageName + "." + servicePath.substring(servicePath.lastIndexOf(".") + 1);
    }
}
