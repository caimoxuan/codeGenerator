package com.cmx.creater.codegenerator.enums;

import com.cmx.creater.codegenerator.template.BeanCreater;
import com.cmx.creater.codegenerator.template.DaoCreater;
import com.cmx.creater.codegenerator.template.MapperCreater;
import lombok.Getter;

/**
 * @author cmx
 * @date 2019/4/3
 */
@Getter
public enum CodeCreateType {

    /**
     * index , create type, class type
     */
    BEAN_CREATE(0, "bean", BeanCreater.class),

    DAO_CREATE(1, "dao", DaoCreater.class),

    MAPPER_CREATE(2, "mapper", MapperCreater.class);

    private int code;

    private String key;

    private Class clazz;

    CodeCreateType(int code, String key, Class createType){
        this.code = code;
        this.key = key;
        this.clazz = createType;
    }

    public static CodeCreateType getType(String key){
        for(CodeCreateType type : CodeCreateType.values()){
            if(type.getKey().equals(key)){
                return type;
            }
        }

        return null;
    }


    public static void main(String[] args){
        System.out.println(getType("dao").getClazz());
    }

}
