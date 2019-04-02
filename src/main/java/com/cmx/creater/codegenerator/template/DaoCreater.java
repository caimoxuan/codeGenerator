package com.cmx.creater.codegenerator.template;


import com.cmx.creater.codegenerator.common.Table;
import com.cmx.creater.codegenerator.utils.NameUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DaoCreater extends Creater implements CodeCreater{

	@Override
	public Map<String, ByteArrayOutputStream> createCode(List<Table> tables) {
		Map<String, ByteArrayOutputStream> beanStream = new HashMap<>(tables.size());
		for (Table t : tables) {
			String content = daoCodeCreater(NameUtil.getBeanName(t.getTableName()));
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
				return daoCodeCreater(NameUtil.getBeanName(tableName));
			}
		}
		return null;
	}
	
	
	public String daoCodeCreater(String beanName){
		StringBuilder sb = new StringBuilder();
		String packageName = config.getDaoPath();
		String beanPath = config.getDomainPath();
		String suffix = config.getDaoSuffix();
		sb.append("package " + packageName + ";\n\n");
		sb.append("import java.util.List;\n\n");
		//sb.append("import " + baseDaoPackage+".BaseDao;\n");
		sb.append("import " + beanPath + "." + beanName + ";\n\n");
		
		sb.append("public interface " + beanName + suffix + /**" extends BaseDao<" + beanName + ">*/ "{\n\n");

		sb.append("\tList<" + beanName + "> select("+ beanName + " "+ NameUtil.getLowCaseName(beanName) +");\n\n");
		sb.append("\tint update("+ beanName + " "+ NameUtil.getLowCaseName(beanName) + ");\n\n");
		sb.append("\tint insert("+ beanName + " "+ NameUtil.getLowCaseName(beanName) +");\n\n");
		sb.append("\tint delete("+ beanName + " "+ NameUtil.getLowCaseName(beanName) +");\n\n");
		sb.append("\t" + beanName + " getById(Object id);\n");

		sb.append("}");

		return sb.toString();
	}
}
