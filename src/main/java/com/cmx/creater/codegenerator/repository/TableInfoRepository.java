package com.cmx.creater.codegenerator.repository;

import com.cmx.creater.codegenerator.common.Column;
import com.cmx.creater.codegenerator.common.Table;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cmx
 * @Date 2019/4/1 22:41
 */
@Component
public class TableInfoRepository {


    public List<Table> getTableName(Connection connection)throws SQLException {
        List<Table> tables = new ArrayList<>();
        DatabaseMetaData dbMetData = connection.getMetaData();
        ResultSet rs = dbMetData.getTables(null, null, null, new String[]{"TABLE", "VIEW"});

        while(rs.next()){
            // || "VIEW".equals(rs.getString(4)) 这里不支持视图
            if("TABLE".equals(rs.getString(4))){
                String tableName = rs.getString(3);
                Table table = new Table(tableName);
                ResultSet colRet = dbMetData.getColumns(null, "%", tableName, "%");
                Map<String, Column> columns = new HashMap<>();
                //根据表名提取表信息
                while (colRet.next()) {
                    String columnName = colRet.getString("COLUMN_NAME");
                    String columnType = colRet.getString("TYPE_NAME");
                    String remarks = colRet.getString("REMARKS");
                    int dataSize = colRet.getInt("COLUMN_SIZE");
                    int nullAble = colRet.getInt("NULLABLE");

                    columns.put(columnName, new Column(columnName, columnType, dataSize, nullAble, remarks));
                }
                //设置表字段
                table.setColumns(columns);

                ResultSet primaryKeySet = dbMetData.getPrimaryKeys(null, null, tableName);
                List<Column> primaryList = new ArrayList<>();
                //获取数据库表的主键
                while(primaryKeySet.next()){
                    String primaryKey = primaryKeySet.getString("COLUMN_NAME");
                    primaryList.add(columns.get(primaryKey));
                }

                table.setPrimaryKeys(primaryList);
                tables.add(table);
            }
        }
        return  tables;
    }


}
