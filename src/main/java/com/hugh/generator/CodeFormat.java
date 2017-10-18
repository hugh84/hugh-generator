package com.hugh.generator;

/**
 * 格式化代码
 * @author Hugh
 *
 */
public class CodeFormat{
	
	/**
	 * mapperXml格式化
	 * @param content
	 * @return
	 */
	public static String mapperXmlFormat(String content) {
		//将<if></if>格式化为一行
		content=content.replaceAll("!= null\">\r\n\\s*", "!= null\">");
		content=content.replaceAll("\r\n\\s*</if>", "</if>");
		//在生成的xml文件最后添加一行分割线
		content+="\r\n\r\n<!-- — — — — — — — — — — — — — — — — — — — — user-defined begin — — — — — — — — — — — — — — — — — — — — -->\r\n\r\n";
		return content;
	}
	
	/**
	 * model格式化
	 * @param content
	 * @return
	 */
	public static String modelFormat(String content) {
		//将属性定义之间的空行去掉
		content=content.replaceAll(";\r\n\r\n\\s*private", ";\r\n    private");
		//将set、get方法之间的空行去掉
		content=content.replaceAll("}\r\n\r\n\\s*public", "}\r\n    public");
		return content;
	}
}
