package com.bcyj99.sirius.core.bus.product.service;

import com.bcyj99.sirius.core.bus.product.vo.BusProduct;

public interface BusProductService {
	BusProduct queryProductById(Integer id);
	
	int addProduct(BusProduct product);
}
