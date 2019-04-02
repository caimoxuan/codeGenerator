package com.cmx.creater.codegenerator.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author cmx
 * 文件创建工具
 */
public final class FileCreateUtil {

	/**
	 * create file at local server
	 * @param fileName
	 * @param filePath
	 * @param content
	 */
	public static void createFile(String fileName, String filePath, String content) {

		File f  = new File(filePath);
		if(!f.exists()){
			if(!f.mkdirs()) {
				return;
			}
		}

		f = new File(filePath + "\\" + fileName);
		FileWriter fw = null;
		try {
			fw = new FileWriter(f);

			fw.write(content);
			fw.flush();
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			if(fw != null ) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
