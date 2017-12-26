package com.vigekoo.common.utils;

import com.vigekoo.common.exception.AppException;
import com.vigekoo.modules.sys.entity.SysColumn;
import com.vigekoo.modules.sys.entity.SysTable;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author oplus
 * @Description: TODO(代码生成器工具类)
 * @date 2017-6-23 15:07
 */
public class GeneratorUtils {

	public static List<String> getTemplates(){
		List<String> templates = new ArrayList<String>();
		templates.add("templates/generator/Entity.java.vm");
		templates.add("templates/generator/Dao.java.vm");
		templates.add("templates/generator/Dao.xml.vm");
		templates.add("templates/generator/Service.java.vm");
		templates.add("templates/generator/ServiceImpl.java.vm");
		templates.add("templates/generator/Controller.java.vm");
		templates.add("templates/generator/list.html.vm");
		templates.add("templates/generator/list.js.vm");
		return templates;
	}
	
	/**
	 * 生成代码
	 */
	public static void generatorCode(Map<String, String> table,
			List<Map<String, String>> columns, ZipOutputStream zip){
		//配置信息
		Configuration config = getConfig();
		
		//表信息
		SysTable sysTable = new SysTable();
		sysTable.setTableName(table.get("tableName"));
		sysTable.setComments(table.get("tableComment"));
		//表名转换成Java类名
		String className = tableToJava(sysTable.getTableName(), config.getString("tablePrefix"));
		sysTable.setClassName(className);
		sysTable.setClassname(StringUtils.uncapitalize(className));
		
		//列信息
		List<SysColumn> columsList = new ArrayList<>();
		for(Map<String, String> column : columns){
			SysColumn sysColumn = new SysColumn();
			sysColumn.setColumnName(column.get("columnName"));
			sysColumn.setDataType(column.get("dataType"));
			sysColumn.setComments(column.get("columnComment"));
			sysColumn.setExtra(column.get("extra"));
			
			//列名转换成Java属性名
			String attrName = columnToJava(sysColumn.getColumnName());
			sysColumn.setAttrName(attrName);
			sysColumn.setAttrname(StringUtils.uncapitalize(attrName));
			
			//列的数据类型，转换成Java类型
			String attrType = config.getString(sysColumn.getDataType(), "unknowType");
			sysColumn.setAttrType(attrType);
			
			//是否主键
			if("PRI".equalsIgnoreCase(column.get("columnKey")) && sysTable.getPk() == null){
				sysTable.setPk(sysColumn);
			}
			
			columsList.add(sysColumn);
		}
		sysTable.setColumns(columsList);
		
		//没主键，则第一个字段为主键
		if(sysTable.getPk() == null){
			sysTable.setPk(sysTable.getColumns().get(0));
		}
		
		//设置velocity资源加载器
		Properties prop = new Properties();  
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");  
		Velocity.init(prop);

		String entityPrefix=config.getString("entityPrefix");
		String classNameTemp=entityPrefix+sysTable.getClassName();
		String classnameTmep=sysTable.getClassname();
		String pathPrefix=config.getString("pathPrefix");

		//封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("tableName", sysTable.getTableName());
		map.put("comments", sysTable.getComments());
		map.put("pk", sysTable.getPk());
		map.put("className", classNameTemp);
		map.put("classname", classnameTmep);
		map.put("pathPrefix", pathPrefix);
		map.put("pathName", "/"+ sysTable.getClassname().toLowerCase());
		map.put("columns", sysTable.getColumns());
		map.put("package", config.getString("package"));
		map.put("author", config.getString("author"));
		map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        VelocityContext context = new VelocityContext(map);
        
        //获取模板列表
		List<String> templates = getTemplates();
		for(String template : templates){
			//渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, "UTF-8");
			tpl.merge(context, sw);
			
			try {
				//添加到zip
				zip.putNextEntry(new ZipEntry(getFileName(template, classNameTemp, classnameTmep, config.getString("package"))));
				IOUtils.write(sw.toString(), zip, "UTF-8");
				IOUtils.closeQuietly(sw);
				zip.closeEntry();
			} catch (IOException e) {
				throw new AppException("渲染模板失败，表名：" + sysTable.getTableName(), e);
			}
		}
	}
	
	
	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
	}
	
	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if(StringUtils.isNotBlank(tablePrefix)){
			tableName = tableName.replace(tablePrefix, "");
		}
		return columnToJava(tableName);
	}
	
	/**
	 * 获取配置信息
	 */
	public static Configuration getConfig(){
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new AppException("获取配置文件失败，", e);
		}
	}
	
	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String className, String classname, String packageName){
		String packagePath = "main" + File.separator + "java" + File.separator;
		if(StringUtils.isNotBlank(packageName)){
			packagePath += packageName.replace(".", File.separator) + File.separator;
		}
		
		if(template.contains("Entity.java.vm")){
			return packagePath + "entity" + File.separator + className + ".java";
		}
		
		if(template.contains("Dao.java.vm")){
			return packagePath + "dao" + File.separator + className + "Dao.java";
		}

		if(template.contains("Service.java.vm")){
			return packagePath + "service" + File.separator + className + "Service.java";
		}
		
		if(template.contains("ServiceImpl.java.vm")){
			return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}
		
		if(template.contains("Controller.java.vm")){
			return packagePath + "controller" + File.separator + className + "Controller.java";
		}

		if(template.contains("Dao.xml.vm")){
			return "main" + File.separator + "resources" + File.separator + "mapper" + File.separator + "sys" + File.separator + className + "Dao.xml";
		}

		if(template.contains("list.html.vm")){
			return "main" + File.separator + "resources" + File.separator + "views" + File.separator
					+ "modules" + File.separator + "sys" + File.separator + classname + ".html";
		}
		
		if(template.contains("list.js.vm")){
			return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
					+ "modules" + File.separator + "sys" + File.separator + classname + ".js";
		}
		
		return null;
	}

}
