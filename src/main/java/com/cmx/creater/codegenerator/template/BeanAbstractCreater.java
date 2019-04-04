package com.cmx.creater.codegenerator.template;


import com.cmx.creater.codegenerator.common.Column;
import com.cmx.creater.codegenerator.common.Table;
import com.cmx.creater.codegenerator.utils.NameUtil;
import com.cmx.creater.codegenerator.utils.SqlTypeUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cmx
 */
@Slf4j
public class BeanAbstractCreater extends AbstractCreater implements CodeCreater {

    @Override
    public Map<String, ByteArrayOutputStream> createCode(List<Table> tables) {
        Map<String, ByteArrayOutputStream> beanStream = new HashMap<>(tables.size());
        for (Table t : tables) {
            String content = createBeanCode(t);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byteArrayOutputStream.write(content.getBytes());
                beanStream.put(config.getDomainPath().replace(".", "/")
                        + "/" + NameUtil.getBeanName(t.getTableName())
                        + ".java", byteArrayOutputStream);
            } catch (IOException e) {
                log.error("bean create error : {}, table : {}", e, t.getTableName());
            }
        }

        return beanStream;
    }

    @Override
    public String createCodeWithTableName(List<Table> tables, String tableName) {
        for (Table table : tables) {
            if (table.getTableName().equals(tableName)) {
                return createBeanCode(table);
            }
        }
        return null;
    }

    private String createBeanCode(Table table) {
        StringBuilder sb = new StringBuilder();

        String beanName = NameUtil.getBeanName(table.getTableName());

        Map<String, Column> columns = table.getColumns();

        String beanPackage = config.getDomainPath();
        sb.append("package " + beanPackage + ";\n");
        for (Column column : columns.values()) {
            String type = SqlTypeUtil.getJavaType(column.getColumnType());
            if ("Date".equals(type)) {
                sb.append("import java.util.Data;\n");
                break;
            }
        }
        sb.append("\nimport java.io.Serializable;\n");
        sb.append("import lombok.Data;\n");
        if (config.getLombokEnable()) {
            sb.append("\n@Data\n");
        } else {
            sb.append("\n");
        }
        sb.append("public class " + beanName + " implements Serializable {\n\n");
        sb.append("\tprivate static final long serialVersionUID = 1L;\n");
        //生成属性
        columns.forEach(((s, column) -> {
            String type = SqlTypeUtil.getJavaType(column.getColumnType());
            sb.append("\t/** " + column.getRemarks() + "*/\n");
            sb.append("\tprivate " + type + " " + NameUtil.lineToHump(s) + ";\n");
        }));
        sb.append("\n");
        //生成属性的getter和setter
        if(!config.getLombokEnable()) {
            for (Column column: columns.values()) {
                String type = SqlTypeUtil.getJavaType(column.getColumnType());
                String filed = NameUtil.lineToHump(column.getColumnName());
                String filedName = filed.substring(0, 1).toUpperCase() + filed.substring(1);
                sb.append("\tpublic void set" + filedName + "(" + type + " " + filed + "){\n");
                sb.append("\t\tthis." + filed + " = " + filed + ";\n");
                sb.append("\t}\n");
                sb.append("\tpublic " + type + " get" + filedName + "(){\n");
                sb.append("\t\treturn " + filed + ";\n");
                sb.append("\t}\n");
            }
            //生成toString
            sb.append("\tpublic String toString(){\n");
            sb.append("\t\treturn \"" + beanName + "[\"\n");
            int columnSize = columns.size();
            int index = 1;
            for (Column column : columns.values()) {
                String filed = NameUtil.lineToHump(column.getColumnName());
                if (columnSize == index) {
                    sb.append("\t\t+\"" + filed + "=\" + " + filed + " \n");
                } else {
                    sb.append("\t\t+\"" + filed + "=\" + " + filed + " + \",\"\n");
                }
                index ++;
            }
            sb.append("\t\t+\"]\";\n");
            sb.append("\t}");
        }
        sb.append("\n}");

        return sb.toString();

    }


}
