package com.cmx.creater.codegenerator.template;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Creater {

	
	protected Map<String, Object> configMap = new HashMap<>();

	public Map<String, Object> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, Object> configMap) {
		Set<String> keySet = configMap.keySet();
		for(String s : keySet){
			this.configMap.put(s, configMap.get(s));
		}
	}
	
	protected Creater(){
		FileSystemView fsv = FileSystemView.getFileSystemView();
		File desktop = fsv.getHomeDirectory();
		configMap.put("filePath", desktop);
	}
	
	
}
