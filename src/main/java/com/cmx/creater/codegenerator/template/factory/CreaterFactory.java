package com.cmx.creater.codegenerator.template.factory;

import com.cmx.creater.codegenerator.template.CodeCreater;

/**
 * @author cmx
 * @date 2019/4/2
 */
public class CreaterFactory {


    public static CodeCreater getCodeCreater(Class clazz){
        try {

            return (CodeCreater)clazz.newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }


}
