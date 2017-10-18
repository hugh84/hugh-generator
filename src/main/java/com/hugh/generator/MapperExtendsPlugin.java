package com.hugh.generator;

import java.util.List;
import java.util.Properties;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 生成的Mapper自动添加extends
 * @author Hugh
 *
 */
public class MapperExtendsPlugin extends PluginAdapter {

	private String mapperExtend;
	
	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}
	
	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		mapperExtend = this.properties.getProperty("mapperExtend");
		if(mapperExtend==null)
			throw new RuntimeException("MapperExtendsPlugin缺少必要的属性配置!");
	}

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass,IntrospectedTable introspectedTable) {
		FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		interfaze.addImportedType(entityType);
		interfaze.addImportedType(new FullyQualifiedJavaType(mapperExtend));
		interfaze.addSuperInterface(new FullyQualifiedJavaType(mapperExtend + "<" + entityType.getShortName()+">"));
		interfaze.getMethods().clear();
		return true;
	}

}
