package com.bcyj99.sirius.core;

import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bcyj99.sirius.core.sys.service.CacheService;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
@ContextConfiguration(locations={"classpath*:conf/spring-core.xml"}) //加载配置文件
public class CacheServiceImplTest {

	@Autowired
	private CacheService<String> cacheService;
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@Test
	public void testGet() {
//		cacheService.set("aa", "789456");
		
		String aa = cacheService.get("fahaiAuthCompanyDetailUrl");
		System.out.println("==========================aa:"+aa);
		
//		Set<String> set = cacheService.getAllKeys();
//		System.out.println("==========================set.size():"+set.size());
//		for(String str : set) {
//			System.out.println("=========================="+str);
//		}
	}

	@Test
	public void testSet() {
		ValueOperations<String, String>  ops = redisTemplate.opsForValue();
//		ops.set("aa", "nttxzz");
		
		String aa = ops.get("fahaiAuthCompanyDetailUrl");
		System.out.println("=========================="+aa);
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetList() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetList() {
		fail("Not yet implemented");
	}

}
