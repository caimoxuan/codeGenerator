package com.cmx.creater.codegenerator.util;

import org.apache.commons.text.StringEscapeUtils;

/**
 * @author cmx
 * @date 2019/4/3
 */
public final class HtmlCodeUtil {

    private static final String LINE_FLAG = "<br />";

    private static final String WHITE_SPACE = "&nbsp;&nbsp;";

    /**
     * 转换成html能显示的格式
     * @param code
     * @return
     */
    public static String parseCodeToHtml(String code){
        if(code == null){
            return null;
        }

        return code.replace("\n", LINE_FLAG).replace("\t", WHITE_SPACE);
    }

    /**
     * 处理xml 文件的输出 < > 特殊符号的转换
     * @param code
     * @return
     */
    public static String parseXmlCode(String code){

        if(code == null){
            return null;
        }

        return StringEscapeUtils.escapeXml11(code).replace("\n", LINE_FLAG).replace("\t", WHITE_SPACE);
    }


}
