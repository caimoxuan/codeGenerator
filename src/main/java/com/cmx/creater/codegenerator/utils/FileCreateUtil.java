package com.cmx.creater.codegenerator.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author cmx
 * 文件创建工具
 */
public final class FileCreateUtil {
	
	private static File f;
		
	private static FileWriter fw;
	
	public static void createFile(String fileName, String filePath, String content) {
		
		f  = new File(filePath);
		if(f.exists()){
			System.out.println("路径存在: " + filePath);
		}else{
			if(f.mkdirs()) {
				System.out.println("创建路径成功");
			}
			else {
				System.out.println("创建失败");
			}
		}
		
		System.out.println("创建文件："+filePath+"\\"+fileName);
		f = new File(filePath+"\\"+fileName);

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
