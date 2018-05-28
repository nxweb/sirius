package com.bcyj99.sirius.core.amqp.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bcyj99.sirius.core.amqp.service.SpringAmqpService;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
@ContextConfiguration(locations={"classpath*:conf/spring-core.xml"}) //加载配置文件
public class SpringAmqpServiceImplTest {
	@Autowired
	private SpringAmqpService springAmqpService;

	@Test
	public void test() {
		springAmqpService.test();
	}

}
