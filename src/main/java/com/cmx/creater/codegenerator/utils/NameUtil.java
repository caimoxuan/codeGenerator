package com.cmx.creater.codegenerator.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cmx
 */
public final class NameUtil {

	private static final Pattern LINE_PATTERN = Pattern.compile("_(\\w)");

	private static final String TABLE_PREFIX = "t_";

	private static final String SPLIT_CHAR = "_";

	/**
	 * t_account_flow -> AccountFlow
	 * @param tableName
	 * @return
	 */
	public static String getBeanName(String tableName){
		
		tableName = tableName.toLowerCase();

		if(tableName.startsWith(TABLE_PREFIX)){
			tableName = tableName.substring(2);
		}

		StringBuilder newStr = new StringBuilder(tableName.substring(0, 1).toUpperCase() + tableName.substring(1));

		if(newStr.indexOf(SPLIT_CHAR) != -1){
			String[] splitStr = newStr.toString().split(SPLIT_CHAR);
			newStr = new StringBuilder();
			for (String aSplitStr : splitStr) {
				newStr.append(aSplitStr.substring(0, 1).toUpperCase()).append(aSplitStr.substring(1));
			}
		}

		return newStr.toString();
	}

	/**
	 * AccountFlow -> accountFlow
	 * @param name
	 * @return
	 */
	public static String getLowCaseName(String name){
		return name.substring(0, 1).toLowerCase() + name.substring(1, name.length());
	}

	/**
	 * user_name -> userName
	 * @param str
	 * @return
	 */
	public static String lineToHump(Object str){
		str = str.toString().toLowerCase();
		Matcher matcher = LINE_PATTERN.matcher((String)str);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
}
