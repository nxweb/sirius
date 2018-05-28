package com.bcyj99.sirius.core.bus.order.dao;

import com.bcyj99.sirius.core.bus.order.vo.BusOrder;

public interface BusOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(BusOrder record);

    int insertSelective(BusOrder record);

    BusOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BusOrder record);

    int updateByPrimaryKey(BusOrder record);
}