package com.cmx.creater.codegenerator.template;

import com.cmx.creater.codegenerator.common.GeneratorConfig;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.HashMap;
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
	
	
}
