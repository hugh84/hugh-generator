package com.hugh.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * Service及Service Implement代码生成
 * @author Hugh
 *
 */
public class ServiceGeneratorPlugin extends PluginAdapter {

	private String targetProject;
	private String targetPackage;
	private String serviceExtend;
	private String serviceImplExtend;
	
	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}
	
	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		targetProject = this.properties.getProperty("targetProject");
		targetPackage = this.properties.getProperty("targetPackage");
		serviceExtend = this.properties.getProperty("serviceExtend");
		serviceImplExtend = this.properties.getProperty("serviceImplExtend");
		if(targetProject==null || targetPackage==null || serviceExtend==null || serviceImplExtend==null)
			throw new RuntimeException("ServiceGeneratorPlugin缺少必要的属性配置!");
	}
	
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		List<GeneratedJavaFile> list = new ArrayList<GeneratedJavaFile>();
		FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
		String shortName=entityType.getShortName();
		
		//生成service
		FullyQualifiedJavaType serviceFullyQualifiedJavaType=new FullyQualifiedJavaType(targetPackage+"."+shortName+"Service");
		Interface serviceInter =new Interface(serviceFullyQualifiedJavaType);
		serviceInter.setVisibility(JavaVisibility.PUBLIC);
		serviceInter.addImportedType(entityType);
		serviceInter.addImportedType(new FullyQualifiedJavaType(serviceExtend));
		serviceInter.addSuperInterface(new FullyQualifiedJavaType(serviceExtend+ "<"+shortName+">"));
		GeneratedJavaFile serviceGeneratedJavaFile=new GeneratedJavaFile((CompilationUnit)serviceInter,targetProject,introspectedTable.getContext().getJavaFormatter());
		list.add(serviceGeneratedJavaFile);
		
		//生成service impl
		FullyQualifiedJavaType serviceImplFullyQualifiedJavaType=new FullyQualifiedJavaType(targetPackage+".impl."+shortName+"ServiceImpl");
		TopLevelClass serviceImplInter =new TopLevelClass(serviceImplFullyQualifiedJavaType);
		serviceImplInter.setVisibility(JavaVisibility.PUBLIC);
		serviceImplInter.addImportedType(entityType);
		serviceImplInter.addImportedType(new FullyQualifiedJavaType(serviceImplExtend));
		serviceImplInter.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
		serviceImplInter.addImportedType(serviceFullyQualifiedJavaType);
		serviceImplInter.addSuperInterface(serviceFullyQualifiedJavaType);
		serviceImplInter.setSuperClass(new FullyQualifiedJavaType(serviceImplExtend+ "<"+shortName+">"));
		serviceImplInter.addAnnotation("@Service");
		GeneratedJavaFile serviceImplGeneratedJavaFile=new GeneratedJavaFile((CompilationUnit)serviceImplInter,targetProject,introspectedTable.getContext().getJavaFormatter());
		list.add(serviceImplGeneratedJavaFile);
		
        return list;
    }
}
