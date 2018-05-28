package com.bcyj99.sirius.scheduler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
@ContextConfiguration(locations={"classpath*:spring-core.xml","classpath*:spring-mybatis.xml"}) //加载配置文件  
public class BusOrderServiceTest {
	@Test
	public void test() {
		System.out.println(System.getProperty("user.dir"));
	}
}
