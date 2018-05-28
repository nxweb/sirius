package com.bcyj99.sirius.core;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bcyj99.sirius.core.bus.order.service.BusOrderService;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
//@ContextConfiguration(locations={"classpath*:spring-core.xml","classpath*:spring-mybatis.xml"}) //加载配置文件  
@ContextConfiguration(locations={"classpath*:conf/spring-core.xml"}) //加载配置文件
public class BusOrderServiceTest {
	
	@Autowired
	private BusOrderService busOrderService;
	
	@Test
	public void test() {
//		System.out.println(System.getProperty("user.dir"));
		busOrderService.quereyOrderById(100L);
	}
	
	@Test
	public void test2() {
		BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
		System.out.println(bcryptPasswordEncoder.encode("123456"));
		System.out.println(bcryptPasswordEncoder.matches("123456", "$2a$16$VSMjRS0rJ0oeCw/aGIrlrOjOHC3ldd6sea1U8swwdjYtKTU3VZX4G"));
	}
	
	@Test
	public void test3() {
		System.out.println(System.currentTimeMillis());
	}
	
	@Test
	public void test4() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		
		cal.add(Calendar.MONTH, 1);
		System.out.println("======================"+cal.getTime());
		
		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		
		cal.add(Calendar.MONTH, 2);
		System.out.println("======================"+cal.getTime());
		
		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2017);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		
		cal.add(Calendar.MONTH, 3);
		System.out.println("======================"+cal.getTime());
	}
}
