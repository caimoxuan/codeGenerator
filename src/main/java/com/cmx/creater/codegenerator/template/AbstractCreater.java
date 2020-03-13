package com.cmx.creater.codegenerator.template;

import com.cmx.creater.codegenerator.common.GeneratorConfig;
import org.springframework.util.CollectionUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author cmx
 */
public abstract class AbstractCreater {


	Map<String, Object> configMap = new HashMap<>();

	GeneratorConfig config = new GeneratorConfig();

	public void setGeneratorConfig(GeneratorConfig config){
		this.config = config;
	}


	public void setConfigMap(Map<String, Object> configMap) {
		Set<String> keySet = configMap.keySet();
		for(String s : keySet){
			this.configMap.put(s, configMap.get(s));
		}
	}
	
	protected AbstractCreater(){
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File desktop = fsv.getHomeDirectory();
		configMap.put("filePath", desktop);
	}


	/**
	 * 为生成代码添加注释
	 * @param sb
	 * @param notesParam 注释的解释
	 * @param params 方法参数
	 * @param result 方法返回值
	 */
	protected void appendNotes(StringBuilder sb, String notesParam, List<String> params, String result) {
		if(sb == null) {
			return;
		}
		sb.append("\n\n");
		sb.append("/** \n");
		sb.append(" * ").append(notesParam).append("\n");
		if(!CollectionUtils.isEmpty(params)) {
			for(String p : params) {
				sb.append(" * @param ").append(p).append("\n");
			}
		}
		if(result != null) {
			sb.append(" * @return ").append(result).append("\n");
		}
		sb.append("*/ \n");
	}

	protected void appendAuthor(StringBuilder sb, String authName) {
		if(sb == null) {
			return;
		}
		sb.append("/**\n").
				append(" * @author ").append(authName).append("\n").
			append(" */\n");
	}

	
	
}
