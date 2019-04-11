package com.cmx.creater.codegenerator.template;


import com.cmx.creater.codegenerator.common.Column;
import com.cmx.creater.codegenerator.common.Table;
import com.cmx.creater.codegenerator.util.NameUtil;
import com.cmx.creater.codegenerator.util.SqlTypeUtil;
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
public class DaoAbstractCreater extends AbstractCreater implements CodeCreater{

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
		sb.append("import " + config.getDomainPath() + "." + beanName + ";\n\n");
		sb.append("@Repository\n");
		sb.append("public interface " + beanName + config.getDaoSuffix() + /**" extends BaseDao<" + beanName + ">*/ "{\n\n");

		sb.append("\tList<" + beanName + "> select("+ beanName + " "+ NameUtil.getLowCaseName(beanName) +");\n\n");
		sb.append("\tint update("+ beanName + " "+ NameUtil.getLowCaseName(beanName) + ");\n\n");
		sb.append("\tint insert("+ beanName + " "+ NameUtil.getLowCaseName(beanName) +");\n\n");
		sb.append("\tint delete("+ beanName + " "+ NameUtil.getLowCaseName(beanName) +");\n\n");

		//TODO 多主键之后再考虑
		Column primaryKey = table.getPrimaryKeys().get(0);

		sb.append("\t" + beanName + " getById(" + SqlTypeUtil.getJavaType(primaryKey.getColumnType()) + " " + NameUtil.lineToHump(primaryKey.getColumnName()) + ");\n");

		sb.append("}");

		return sb.toString();
	}
}
