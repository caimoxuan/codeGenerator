package com.cmx.creater.codegenerator.template;

import com.cmx.creater.codegenerator.common.Column;
import com.cmx.creater.codegenerator.common.Table;
import com.cmx.creater.codegenerator.utils.FileCreateUtil;
import com.cmx.creater.codegenerator.utils.NameUtil;
import com.cmx.creater.codegenerator.utils.SqlTypeUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class MapperCreater extends Creater implements CodeCreater{


	@Override
	public Map<String, ByteArrayOutputStream> createCode(List<Table> tables) {
		Map<String, ByteArrayOutputStream> mapperStream = new HashMap<>(tables.size());
		for(Table t : tables){
			String content = mapperCodeCreater(t);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				byteArrayOutputStream.write(content.getBytes());
				mapperStream.put(config.getMapperPath().replace(".", "/")
						+ "/" + NameUtil.getLowCaseName(NameUtil.getBeanName(t.getTableName()))
						+ "Mapper.xml", byteArrayOutputStream);
			} catch (IOException e) {
				log.error("mapper create error : {}, table : {}", e, t.getTableName());
			}

		}

		return mapperStream;
	}

	@Override
	public String createCodeWithTableName(List<Table> tables, String tableName) {
		if(tableName == null){
			return null;
		}
		for(Table t : tables){
			if(tableName.equals(t.getTableName())){
				return 	mapperCodeCreater(t);
			}
		}

		return null;
	}


	private String mapperCodeCreater(Table table){

		Map<String, Column> columns = table.getColumns();

		List<Column> primaryKeys = table.getPrimaryKeys();
		//TODO 处理多主键生成问题 目前只取一个
		String primaryKey = primaryKeys.get(0).getColumnName();

		String tableName = table.getTableName();

		String beanName = NameUtil.getBeanName(tableName);

		StringBuilder sb = new StringBuilder();
		List<String> configNameList = new ArrayList<>();

		columns.forEach((columnName, column) -> configNameList.add(columnName));
		
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		sb.append("<mapper namespace=\""+ config.getDaoPath().replace("\\", ".") + "." + beanName + config.getDaoSuffix()+"\">\n");
		//resultMap
		sb.append("\t<resultMap id=\"BaseResultMap\" type=\"" + beanName + "\" >\n");
		columns.forEach((columnName, column) -> sb.append("\t\t<result column=\""+ columnName +"\" property=\""+ NameUtil.lineToHump(columnName)+"\" />\n"));
		sb.append("\t</resultMap>\n\n");

		sb.append("\t<sql id = \"table\">\n\t\t"+tableName+"\n\t</sql>\n");
		//selectId start
		sb.append("\n\t<sql id = \"columns\">\n\t\t");
		for(int i = 0; i < configNameList.size(); i++){
			if(i != configNameList.size()-1){
				sb.append(configNameList.get(i) + ", ");
				if((i+1) % 5 == 0){
					sb.append("\n\t\t");
				}
			}else{
				sb.append(configNameList.get(i) + "\n");
			}
		}
		sb.append("\t</sql>\n");

		sb.append("\n\t<sql id = \"values\">\n\t\t");
		for(int i = 0; i < configNameList.size(); i++){
			if(i != configNameList.size()-1){
				sb.append("#{" + NameUtil.lineToHump(configNameList.get(i)) + "}, ");
				if((i+1) % 5 == 0){
					sb.append("\n\t\t");
				}
			}else{
				sb.append("#{" + NameUtil.lineToHump(configNameList.get(i)) + "}\n");
			}
		}
		sb.append("\t</sql>\n");
		//selectId end
		sb.append("\n");
		//select start
		sb.append("\t<select id = \"select\" parameterType = \""+ beanName +"\" resultMap = \"BaseResultMap\">\n");
		sb.append("\t\tselect <include refid = \"columns\" />\n");
		sb.append("\t\tfrom <include refid=\"table\"/> \n");
		sb.append("\t\t<where>\n");
		for (String columnName : configNameList) {
			if ("String".equals(SqlTypeUtil.getJavaType(columns.get(columnName).getColumnType()))) {
				sb.append("\t\t\t<if test = \"" + NameUtil.lineToHump(columnName) + " != null and " + NameUtil.lineToHump(columnName) + " != '' \">");
			} else {
				sb.append("\t\t\t<if test = \"" + NameUtil.lineToHump(columnName) + " != null\">");
			}
			sb.append("AND " + columnName + " = " + "#{" + NameUtil.lineToHump(columnName) + "}");
			sb.append("</if>\n");
		}
		sb.append("\t\t</where>\n");
		sb.append("\t</select>\n");
		//select end
		sb.append("\n");
		//insert start
		sb.append("\t<insert id = \"insert\" keyProperty = \"id\" parameterType = \""+ beanName +"\" useGeneratedKeys = \"true\">\n");
		sb.append("\t\tinsert into <include refid=\"table\"/>(<include refid=\"columns\"/>)\n");
		sb.append("\t\tvalues (<include refid=\"values\"/>)\n");
		sb.append("\t</insert>\n");
		//insert end
		sb.append("\n");
		//delete start 
		sb.append("\t<delete id = \"delete\" parameterType = \"String\">\n");
		sb.append("\t\tdelete from <include refid=\"table\"/>\n\t\twhere "+ primaryKey +" = #{"+ NameUtil.lineToHump(primaryKey) +"}\n");
		sb.append("\t</delete>\n");
		//delete end
		sb.append("\n");
		//update start
		sb.append("\t<update id = \"update\" parameterType = \""+ beanName +"\">\n");
		sb.append("\t\tupdate <include refid=\"table\"/>\n");
		sb.append("\t\t<trim prefix=\"SET\" suffixOverrides=\",\">\n");
		for (String columnName : configNameList) {
			if ("String".equals(SqlTypeUtil.getJavaType(columns.get(columnName).getColumnType()))) {
				sb.append("\t\t\t<if test = \"" + NameUtil.lineToHump(columnName) + " != null and " + NameUtil.lineToHump(columnName) + " != '' \">");
			} else {
				sb.append("\t\t\t<if test = \"" + NameUtil.lineToHump(columnName) + " != null\">");
			}
			sb.append(columnName + " = #{" + NameUtil.lineToHump(columnName) + "},");
			sb.append("</if>\n");
		}
		sb.append("\t\t</trim>\n");
		sb.append("\t\twhere "+ primaryKey +" = #{"+ NameUtil.lineToHump(primaryKey) +"}\n");
		sb.append("\t</update>\n");
		//update end
		sb.append("\n");
		//getById start 
		sb.append("\t<select id = \"getById\" resultMap = \"BaseResultMap\">\n");
		sb.append("\t\tselect <include refid = \"columns\" />\n");
		sb.append("\t\tfrom <include refid = \"table\" />\n");
		sb.append("\t\twhere\n");
		sb.append("\t\t"+ primaryKey +" = #{"+ NameUtil.lineToHump(primaryKey) +"}\n");
		sb.append("\t</select>\n");
		//getById end
		sb.append("\n</mapper>");

		return sb.toString();
	}

}
