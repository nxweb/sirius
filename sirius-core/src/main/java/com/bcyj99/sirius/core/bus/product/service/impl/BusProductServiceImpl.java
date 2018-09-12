package com.bcyj99.sirius.core.bus.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcyj99.sirius.core.bus.product.dao.BusProductMapper;
import com.bcyj99.sirius.core.bus.product.service.BusProductService;
import com.bcyj99.sirius.core.bus.product.vo.BusProduct;

@Service
public class BusProductServiceImpl implements BusProductService {
	@Autowired
	private BusProductMapper busProductMapper;

	@Override
	public BusProduct queryProductById(Integer id) {
		BusProduct busProduct = busProductMapper.selectByPrimaryKey(id);
		return busProduct;
	}

	@Override
	public int addProduct(BusProduct product) {
		int count = busProductMapper.insert(product);
		return count;
	}

}
