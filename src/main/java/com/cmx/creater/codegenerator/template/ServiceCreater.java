package com.cmx.creater.codegenerator.template;


import com.cmx.creater.codegenerator.common.Table;
import com.cmx.creater.codegenerator.utils.FileCreateUtil;
import com.cmx.creater.codegenerator.utils.NameUtil;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class ServiceCreater extends Creater implements CodeCreater{



	@Override
	public Map<String, ByteArrayOutputStream> createCode(List<Table> tables) {
		return null;
	}

	@Override
	public String createCodeWithTableName(List<Table> tables, String tableName) {
		return null;
	}
	
	
	public String createServiceInterface(String beanName){
		String packageName = config.getServicePath();
		String beanPath = config.getDomainPath();
		String basePath = config.getBasePath();
 		StringBuffer sb = new StringBuffer();
 		sb.append("package " + packageName + ";\n\n");
 		sb.append("import " + beanPath + "." + beanName + ";\n");
 		sb.append("import " + basePath + ".BaseService;\n\n" );
 		sb.append("public interface " + beanName + "Service extends BaseService<" + beanName + "> {\n\n");
 		
 		sb.append("\n}");
 		return sb.toString();
	}
	
	//使用spring的Service声明
	public String createServiceImpl(String beanName){
		String packageName = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("servicePath");
		String beanPath = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("beanPath").toString();
		String basePath = configMap.get("basePackageName").toString().replace("\\", ".") + "." + configMap.get("basePath").toString();
		
		StringBuffer sb = new StringBuffer();
		sb.append("package " + packageName + ".impl;\n\n");
		
		sb.append("import org.springframework.stereotype.Service;\n");
		sb.append("import " + packageName + "." + beanName + "Service;\n");
		sb.append("import " + beanPath + "." + beanName + ";\n");
 		sb.append("import " + basePath + ".BaseServiceImpl;\n\n" );
 		
 		sb.append("@Service(\""+beanName.substring(0,1).toLowerCase()+beanName.substring(1) + "Service\")\n");
 		sb.append("public class " + beanName + "ServiceImpl extends BaseServiceImpl<" + beanName + "> implements " + beanName + "Service {\n\n");
 		sb.append("\n}");
		
 		String filePath = configMap.get("filePath").toString() + "\\" + packageName.replace(".", "\\") + "\\impl";
 		try{
 			FileCreateUtil.createFile(beanName+"ServiceImpl.java", filePath, sb.toString());
 		}catch(Exception e){
 			e.printStackTrace();
 		}
		return sb.toString();
	}
}
