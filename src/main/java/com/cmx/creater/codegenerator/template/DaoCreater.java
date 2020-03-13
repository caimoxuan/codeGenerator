package com.cmx.creater.codegenerator.template;


import com.cmx.creater.codegenerator.common.Column;
import com.cmx.creater.codegenerator.common.Table;
import com.cmx.creater.codegenerator.util.NameUtil;
import com.cmx.creater.codegenerator.util.SqlTypeUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cmx
 */
@Slf4j
public class DaoCreater extends AbstractCreater implements CodeCreater{

	@Override
	public Map<String, ByteArrayOutputStream> createCode(List<Table> tables) {
		Map<String, ByteArrayOutputStream> beanStream = new HashMap<>(tables.size());
		for (Table t : tables) {
			String content = daoCodeCreater(t);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			try {
				byteArrayOutputStream.write(content.getBytes());
				beanStream.put(config.getDaoPath().replace(".", "/")
						+ "/" + NameUtil.getBeanName(t.getTableName()) + config.getDaoSuffix()
						+ ".java", byteArrayOutputStream);
			} catch (IOException e) {
				log.error("dao create error : {}, table : {}", e, t.getTableName());
			}
		}

		return beanStream;
	}

	@Override
	public String createCodeWithTableName(List<Table> tables, String tableName) {

		for(Table table : tables){
			if(table.getTableName().equals(tableName)){
				return daoCodeCreater(table);
			}
		}
		return null;
	}
	
	
	public String daoCodeCreater(Table table){

		String beanName = NameUtil.getBeanName(table.getTableName());

		StringBuilder sb = new StringBuilder();
		sb.append("package " + config.getDaoPath() + ";\n\n");
		sb.append("import java.util.List;\n\n");
		//sb.append("import " + baseDaoPackage+".BaseDao;\n");
		sb.append("import org.springframework.stereotype.Repository;\n");
		sb.append("import ").append(config.getDomainPath()).append(".").append(beanName).append(";\n\n");
		appendAuthor(sb, config.getAuthor());
		sb.append("@Repository\n");
		sb.append("public interface ").append(beanName).append(config.getDaoSuffix()).append( /**" extends BaseDao<" + beanName + ">*/"{\n\n");
		appendNotes(sb, "select", Collections.singletonList(NameUtil.getLowCaseName(beanName)), "list");
		sb.append("\tList<").append(beanName).append("> select(").append(beanName).append(" ").append(NameUtil.getLowCaseName(beanName)).append(");\n\n");
		appendNotes(sb,"update", Collections.singletonList(NameUtil.getLowCaseName(beanName)), "update count");
		sb.append("\tint update(").append(beanName).append(" ").append(NameUtil.getLowCaseName(beanName)).append(");\n\n");
		appendNotes(sb,"insert", Collections.singletonList(NameUtil.getLowCaseName(beanName)), "insert count");
		sb.append("\tint insert(").append(beanName).append(" ").append(NameUtil.getLowCaseName(beanName)).append(");\n\n");
		appendNotes(sb,"delete", Collections.singletonList("id"), "delete count");

		//TODO 多主键之后再考虑
		Column primaryKey = table.getPrimaryKeys().get(0);
		sb.append("\tint delete(").append(primaryKey.getClass().getName()).append(" ").append("id").append(");\n\n");

		sb.append("\t").append(beanName).append(" getById(").append(SqlTypeUtil.getJavaType(primaryKey.getColumnType())).append(" ").append(NameUtil.lineToHump(primaryKey.getColumnName())).append(");\n");

		sb.append("}");

		return sb.toString();
	}

}
