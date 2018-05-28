package com.bcyj99.sirius.core.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.util.ResourceUtils;

public class MyBatisGeneratorUtil {

	public static void main(String[] args){
		try {
		   List<String> warnings = new ArrayList<String>();
		   boolean overwrite = true;
		   File configFile = ResourceUtils.getFile("classpath:generatorConfig.xml");
		   ConfigurationParser cp = new ConfigurationParser(warnings);
		   Configuration config = cp.parseConfiguration(configFile);
		   DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		   MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		   myBatisGenerator.generate(null);
		   System.out.println("生成成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
