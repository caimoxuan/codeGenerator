package com.cmx.creater.codegenerator.template;

import com.cmx.creater.codegenerator.common.GeneratorConfig;
import lombok.Getter;
import lombok.Setter;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Creater {


	Map<String, Object> configMap = new HashMap<>();

	@Getter
	@Setter
	GeneratorConfig config = new GeneratorConfig();



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
