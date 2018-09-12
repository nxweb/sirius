package com.bcyj99.sirius.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bcyj99.sirius.core.bus.product.service.BusProductService;
import com.bcyj99.sirius.core.bus.product.vo.BusProduct;
import com.bcyj99.sirius.core.sys.service.JedisService;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
@ContextConfiguration(locations={"classpath*:conf/spring-core.xml"}) //加载配置文件
public class BusProductServiceTest {
	@Autowired
	private BusProductService busProductService;
	
	@Autowired
	private JedisService jedisService;

	@Test
	public void testQueryProductById() {
		BusProduct busProduct = busProductService.queryProductById(9);
		System.out.println(busProduct.getId()+":"+busProduct.getPname());
	}

	@Test
	public void testAddProduct() {
		BusProduct product = new BusProduct();
		product.setPname("苹果2");
		busProductService.addProduct(product);
	}
	
	@Test
	public void testJedis() {
		String str = jedisService.getKey("key1");
		System.out.println("================="+str);
	}

}
