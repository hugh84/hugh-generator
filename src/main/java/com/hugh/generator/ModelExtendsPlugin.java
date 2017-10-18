package com.hugh.generator;

import java.util.List;
import java.util.Properties;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * 生成的model自动添加extends
 * @author Hugh
 *
 */
public class ModelExtendsPlugin extends PluginAdapter {

	private String sModelExtend;
	
	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		sModelExtend = this.properties.getProperty("modelExtend");
		if(sModelExtend==null)
			throw new RuntimeException("ModelExtendsPlugin缺少必要的属性配置!");
	}

	@Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		IntrospectedColumn colId=introspectedTable.getColumn("id");
		topLevelClass.addImportedType(new FullyQualifiedJavaType(sModelExtend));
		topLevelClass.setSuperClass(new FullyQualifiedJavaType(sModelExtend+"<"+colId.getFullyQualifiedJavaType()+ ">"));
		return true;
    }

	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}
}
