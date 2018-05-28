package com.bcyj99.sirius.core.product.service;

import static org.junit.Assert.fail;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bcyj99.sirius.core.security.vo.SecuUser;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
@ContextConfiguration(locations={"classpath*:conf/spring-core.xml"}) //加载配置文件
public class BaseProductServiceTest {
	
	@Autowired
	private RedisTemplate<Serializable,Serializable> redisTemplate;

	@Test
	public void testQueryProductById() {
		fail("Not yet implemented");
	}
	
	@Test
	public void test2() {
		ValueOperations<Serializable,Serializable> opsValue = redisTemplate.opsForValue();
		SecuUser a1 = (SecuUser) opsValue.get("a1");
		System.out.println(a1.getUsername());
		String a2 = (String) opsValue.get("a2");
		System.out.println(a2);
	}
	
	@Test
	public void test3() {
		ValueOperations<Serializable,Serializable> opsValue = redisTemplate.opsForValue();
		SecuUser u =new SecuUser();
		u.setId(103L);
		u.setUsername("小米3");
		opsValue.set("a1", u);
		
		opsValue.set("a2", "小明2");
	}
	
	@Test
	public void test4() {
		Set<String> keys = new HashSet<String>();
		keys.add(null);
	}

}
