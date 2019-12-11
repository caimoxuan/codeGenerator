package com.cmx.creater.codegenerator.parser;


import com.cmx.creater.codegenerator.common.Column;
import com.cmx.creater.codegenerator.common.Table;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author wb-cmx582280
 * @date 2019/12/10 17:47
 *  将建表语句解析成 Table 对象 mysql
 */
@Slf4j
@Component
public class CreateTableParser {

    /**
     *  语句开始标记
     */
    private static final String TABLE_START_TAG = "CREATE TABLE";
    /**
     *  语句结束标记
     */
    private static final String SQL_END_TAG = ";";

    private static final String COLUMN_FLAG_TAG = "`";

    private static final String COLUMN_FLAG_TAG_1 = "'";

    private static final String NOT_NULL = "NOT NULL";

    private static final String COMMENT = "COMMENT";

    private static final String INDEX = "INDEX";

    private static final String PRIMARY_KEY = "PRIMARY KEY";

    private static final String LEFT_BRACKETS = "(";

    private static final String RIGHT_BRACKETS = ")";

    private static final String ELEMENT_SPLIT = ",";


    public List<Table> parseSqlToTable(Reader reader) throws Exception {
        List<String> tableList = parseSqlFile(reader);
        List<Table> tables = new ArrayList<>(tableList.size());
        if(!CollectionUtils.isEmpty(tableList)) {
            for(String tableStr : tableList) {
                Table table = parseTable(tableStr);
                if(table != null) {
                    tables.add(table);
                }
            }
        }
        return tables;
    }

    private Table parseTable(String tableCreateSql) throws Exception {
        if(tableCreateSql == null) {
            return null;
        }
        if(checkSql(tableCreateSql)) {
            return parseCreateSql(tableCreateSql);
        }
        return null;
    }

    private List<String> parseSqlFile(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        List<String> tableSql = new ArrayList<>();
        String str;
        boolean tableTag = false;
        StringBuilder tableStr = new StringBuilder();
        while((str = br.readLine()) != null) {
            if(tableTag) {
                if(str.endsWith(SQL_END_TAG)) {
                    tableStr.append(str);
                    tableSql.add(tableStr.toString());
                    tableStr.delete(0, tableStr.length());
                    tableTag = false;
                } else {
                    tableStr.append(str).append("\n");
                }
            }
            if(str.startsWith(TABLE_START_TAG)) {
                tableTag = true;
                tableStr.append(str).append("\n");
            }
        }
        return tableSql;
    }

    private Table parseCreateSql(String sql) throws Exception {
        String[] lineSql = sql.split("\n");
        String tableName = lineSql[0].substring(lineSql[0].indexOf(COLUMN_FLAG_TAG) + 1, lineSql[0].lastIndexOf(COLUMN_FLAG_TAG));
        Table table = new Table(tableName);
        table.setColumns(new HashMap<>(lineSql.length));
        List<String> indexes = null;
        for(int i = 1; i < lineSql.length - 1; i++) {
            String line = lineSql[i].trim();
            // 处理描述column的行
            if(line.contains(PRIMARY_KEY) || line.contains(INDEX)) {
                // 处理描述主键、索引的行
                if(line.startsWith(PRIMARY_KEY)) {
                    indexes = parseIndexAndKey(line);
                }
            } else {
                Column column = parseColumn(line);
                table.getColumns().put(column.getColumnName(), column);
            }
        }
        if(!CollectionUtils.isEmpty(indexes)) {
            table.setPrimaryKeys(new ArrayList<>(indexes.size()));
            indexes.forEach(i -> table.getPrimaryKeys().add(table.getColumns().get(i)));
        }
        return table;
    }


    /**
     * 将描述column的一行字符串解析成column
     * @param lineSql 字符
     * @return Column
     * @throws Exception 可能出错的sql异常
     */
    private Column parseColumn(String lineSql) throws Exception {

        String[] element = lineSql.split(" ");
        try {
            // 列名称
            String columnName = getPureStr(element[0]);
            // 列类型
            String columnType;
            int columnSize = 0;
            if (element[1].contains(LEFT_BRACKETS)) {
                columnType = element[1].substring(0, element[1].indexOf(LEFT_BRACKETS));
                //列大小
                String sizeStr;
                if(element[1].contains(ELEMENT_SPLIT)){
                    sizeStr  = element[1].substring(element[1].indexOf(LEFT_BRACKETS) + 1, element[1].indexOf(ELEMENT_SPLIT));
                } else {
                    sizeStr = element[1].substring(element[1].indexOf(LEFT_BRACKETS) + 1, element[1].indexOf(RIGHT_BRACKETS));
                }
                columnSize = Integer.parseInt(sizeStr);
            } else {
                columnType = element[1];
            }
            // 非空
            Integer nullAble = lineSql.contains(NOT_NULL) ? 0 : 1;
            // 注释
            String comment = null;
            if (lineSql.contains(COMMENT)) {
                comment = getPureStr(lineSql.substring(lineSql.indexOf(COMMENT) + COMMENT.length() + 1));
            }
            return new Column(columnName, columnType, columnSize, nullAble, comment);
        }catch (Exception e) {
            log.info("parse column error : ", e);
            throw new Exception("can't parse sql : " + lineSql + " please check your sql or submit an issue!");
        }
    }


    /**
     * 解析主键， 现在只解析主键 索引忽略
     * @param line
     * @return
     */
    private List<String> parseIndexAndKey(String line) throws Exception {
        try {
            List<String> indexes = new ArrayList<>();
            String primaryKeys = line.substring(line.indexOf(LEFT_BRACKETS) + 1, line.indexOf(RIGHT_BRACKETS));
            String[] split = primaryKeys.split(ELEMENT_SPLIT);
            for (String str : split) {
                if (!StringUtils.isBlank(str)) {
                    indexes.add(getPureStr(str));
                }
            }
            return indexes;
        }catch (Exception e) {
            throw new Exception("can't parse primary key :" + line + "please check sql or submit an issue!");
        }
    }


    /**
     * 检查创建sql的格式是否正确
     * @param tableCreateSql sql
     * @return 是否校验通过
     */
    private boolean checkSql(String tableCreateSql) {
        if(tableCreateSql.startsWith(TABLE_START_TAG) || tableCreateSql.startsWith(TABLE_START_TAG.toLowerCase())){
            return tableCreateSql.endsWith(SQL_END_TAG);
        }
        return false;
    }

    /**
     *  `source` / source -> source
     * @param source
     * @return pureStr
     */
    private String getPureStr(String source) {
        if(StringUtils.isNotBlank(source)) {
            if(source.contains(COLUMN_FLAG_TAG)) {
                return source.substring(1, source.lastIndexOf(COLUMN_FLAG_TAG));
            }
            if(source.contains(COLUMN_FLAG_TAG_1)) {
                return source.substring(1, source.lastIndexOf(COLUMN_FLAG_TAG_1));
            }
        }
        return source;
    }

    public static void main(String[] args) throws Exception {
        CreateTableParser parser = new CreateTableParser();
        //parser.parseCreateSql(getTestStr());
        File file  = new File("C:\\Users\\wb-cmx582280\\Desktop\\zlb_manager.sql");
        List<Table> tables = parser.parseSqlToTable(new FileReader(file));

        tables.forEach(System.out::println);
    }




    private  static String getTestStr() {
        return "CREATE TABLE `t_operation_help`  (\n" +
                "  `help_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',\n" +
                "  `help_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帮助编码',\n" +
                "  `scene` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '场景',\n" +
                "  `operation_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员',\n" +
                "  `sequence` int(11) UNSIGNED NOT NULL COMMENT '排序',\n" +
                "  `help_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帮助标题',\n" +
                "  `help_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '帮助内容',\n" +
                "  `edit_time` datetime(0) NOT NULL COMMENT '编辑时间',\n" +
                "  `description` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',\n" +
                "  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',\n" +
                "  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',\n" +
                "  PRIMARY KEY (`help_id`) USING BTREE,\n" +
                "  INDEX `idx_help_code`(`help_code`) USING BTREE\n" +
                ") ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '运营管理-使用帮助' ROW_FORMAT = Dynamic;";
    }

}
