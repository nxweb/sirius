package com.bcyj99.sirius.core.bus.product.dao;

import com.bcyj99.sirius.core.bus.product.vo.BusProduct;

public interface BusProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BusProduct record);

    int insertSelective(BusProduct record);

    BusProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BusProduct record);

    int updateByPrimaryKey(BusProduct record);
}