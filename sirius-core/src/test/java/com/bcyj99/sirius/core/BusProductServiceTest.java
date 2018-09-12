package com.bcyj99.sirius.core;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bcyj99.sirius.core.bus.product.service.BusProductService;
import com.bcyj99.sirius.core.bus.product.vo.BusProduct;

@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试  
@ContextConfiguration(locations={"classpath*:conf/spring-core.xml"}) //加载配置文件
public class BusProductServiceTest {
	@Autowired
	private BusProductService busProductService;

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

}
